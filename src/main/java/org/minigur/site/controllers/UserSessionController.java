package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserSessionController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Boolean login(HttpServletRequest request, @RequestBody UserSessionRequest userSessionRequest) {
        Boolean alreadyLoggedIn = request.getAttribute("user") != null;
        if (alreadyLoggedIn) {
            return false;
        }
        // TODO: Find if login information valid, get user information.
        User dummyUser = new User(userSessionRequest.getUsername(), false);
        request.setAttribute("user", dummyUser);
        return true;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    Boolean logout(HttpServletRequest request) {
        Boolean notLoggedIn = request.getAttribute("user") == null;
        if (notLoggedIn) {
            return false;
        }
        request.removeAttribute("user");
        return true;
    }
}
