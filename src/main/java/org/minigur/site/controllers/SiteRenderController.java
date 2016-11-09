package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.Environment;
import org.minigur.site.models.Comment;
import org.minigur.site.models.Image;
import org.minigur.site.models.RatingData;
import org.minigur.site.models.User;
import org.minigur.site.service.CommentDAO;
import org.minigur.site.service.ImageDAO;
import org.minigur.site.service.RatingDAO;
import org.minigur.site.service.UserDAO;
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
        List<Comment> comments = commentDAO.getComments(imageId);
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
    String search(HttpServletRequest request, @RequestParam("query") String query) {
        if (redirectToLogin(request)) {
            return "search";
        }
        return "search";
    }
}
