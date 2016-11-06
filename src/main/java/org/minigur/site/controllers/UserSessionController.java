package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.minigur.site.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserSessionController {

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Boolean login(HttpServletRequest request, @RequestBody UserSessionRequest userSessionRequest) {
        Boolean alreadyLoggedIn = request.getSession().getAttribute("user") != null;
        if (alreadyLoggedIn) {
            return false;
        }
        if (!userDAO.checkPassword(userSessionRequest)) {
            return false;
        }
        User currentUser = new User(userSessionRequest.getUsername(), false);
        request.getSession().setAttribute("user", currentUser);
        return true;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    Boolean logout(HttpServletRequest request) {
        Boolean notLoggedIn = request.getSession().getAttribute("user") == null;
        if (notLoggedIn) {
            return false;
        }
        request.getSession().removeAttribute("user");
        return true;
    }
}
