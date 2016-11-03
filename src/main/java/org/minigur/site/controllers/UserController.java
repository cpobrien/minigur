package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.User;
import org.minigur.site.models.UserCreationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    User getUser(@PathVariable("username") String username) {
        return new User(username, false);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Boolean createUser(HttpServletRequest request, @RequestBody UserCreationRequest userCreationRequest) {
        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (userLoggedIn) {
            return false;
        }
        // TODO: Implement user creation
        return true;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    Boolean deleteUser(HttpServletRequest session, @PathVariable("username") String username) {
        User currentUser = (User) session.getSession().getAttribute("user");
        Boolean isUserOwner = currentUser.getUsername().equals(username);
        if (!isUserOwner && !currentUser.getAdmin()) {
            return false;
        }
        return true;
    }

}
