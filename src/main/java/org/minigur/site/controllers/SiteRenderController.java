package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.minigur.site.Environment;
import org.minigur.site.models.*;
import org.minigur.site.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class SiteRenderController {
    @Autowired
    Environment environment;

    @Autowired
    ImageDAO imageDAO;

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    RatingDAO ratingDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    SearchDAO searchDAO;

    private Boolean redirectToLogin(HttpServletRequest request) {
        return request.getSession().getAttribute("user") == null;
    }

    @GetMapping("/")
    String home(HttpServletRequest request, Model model) {
        if (redirectToLogin(request)) {
            return "login";
        }
        model.addAttribute("images", imageDAO.getLatestImages(10));
        return "home";
    }

    @GetMapping("/{imageId}")
    String viewImage(HttpServletRequest request, Model model, @PathVariable("imageId") String imageId) {
        if (redirectToLogin(request)) {
            return "redirect:";
        }
        Image image = imageDAO.findImage(imageId);
        Boolean canDelete = ((User) request.getSession().getAttribute("user"))
                        .getUsername().equals(image.getImageOwner().getUsername())
                        ||
                        ((User) request.getSession().getAttribute("user")).getAdmin();
        System.out.println(canDelete);
        List<Comment> comments = commentDAO.getComments(imageId);
        List<Tag> tags = tagDAO.getTags(imageId);
        model.addAttribute("canDelete", canDelete);
        model.addAttribute("tags", tags);
        model.addAttribute("image", image);
        model.addAttribute("comments", comments);
        model.addAttribute("commentSize", comments.size());
        RatingData rating = ratingDAO.getRating(imageId);
        model.addAttribute("upvotes", rating.getNumberOfUpvotes());
        model.addAttribute("downvotes", rating.getNumberOfDownvotes());

        return "image";
    }

    @GetMapping("/user")
    String viewAccount(HttpServletRequest request) {
        if (redirectToLogin(request)) {
            return "redirect:";
        }
        User user = (User) request.getSession().getAttribute("user");
        return "redirect:user/" + user.getUsername();
    }

    @GetMapping("/user/{userId}")
    String viewUser(HttpServletRequest request, Model model, @PathVariable("userId") String userId) {
        if (redirectToLogin(request)) {
            return "redirect:";
        }
        User pageUser = userDAO.getUser(userId);
        if (pageUser == null) {
            return "redirect:";
        }
        User user = (User) request.getSession().getAttribute("user");
        Boolean isCurrentUser = user.getUsername().equals(userId);
        List<Image> images = imageDAO.getUserImages(userId);
        model.addAttribute("user", pageUser);
        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("images", images);
        return "user";
    }

    @GetMapping("/search")
    String search(HttpServletRequest request,
                  Model model,
                  @RequestParam(value = "query", required = false) String query,
                  @RequestParam(value = "comment", required = false) String comment,
                  @RequestParam(value = "tag", required = false) String tag,
                  @RequestParam(value = "user", required = false) String user) {
        if (redirectToLogin(request)) {
            return "redirect:";
        }
        if (query == null) {
            return "search";
        }
        SearchRequest searchRequest = new SearchRequest(query, true, user != null, comment != null, tag != null);
        List<Image> images = searchDAO.searchImages(searchRequest);
        model.addAttribute("request", searchRequest);
        model.addAttribute("images", images);
        return "search";
    }
}
