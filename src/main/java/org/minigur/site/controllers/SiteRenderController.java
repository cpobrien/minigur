package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.Environment;
import org.minigur.site.service.ImageDAO;
import org.minigur.site.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class SiteRenderController {
    @Autowired
    Environment environment;

    @Autowired
    ImageDAO imageDAO;

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
    String viewImage(HttpServletRequest request) {
        if (redirectToLogin(request)) {
            return "login";
        }
        return "image";
    }

    @GetMapping("/user/{userId}")
    String viewUser(HttpServletRequest request, @PathVariable("userId") String userId) {
        if (redirectToLogin(request)) {
            return "login";
        }
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
