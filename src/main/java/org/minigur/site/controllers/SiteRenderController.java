package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.Environment;
import org.minigur.site.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class SiteRenderController {
    @Autowired
    Environment environment;

    private Boolean redirectToLogin(HttpServletRequest request) {
        return request.getAttribute("user") == null;
    }

    @GetMapping("/")
    String home(HttpServletRequest request) {
        if (redirectToLogin(request)) {
            return "login";
        }
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
            return "login";
        }
        return "search";
    }

    public void print() {
        try {
            ResultSet resultSet = environment
                    .getJdbcManager()
                    .connect()
                    .createStatement()
                    .executeQuery("SELECT * FROM minigur.Image");
            while (resultSet.next()) {
                System.out.printf("The image with url %s, uploaded at time %s, was uploaded by the user with id %s.\n",
                        resultSet.getString("filename"),
                        resultSet.getString("upload_time"),
                        resultSet.getString("owner_user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
