package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.minigur.site.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_api")
public class UserController {
    @Autowired
    UserDAO userDao;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    User getUser(@PathVariable("username") String username) {
        return userDao.getUser(username);
    }

    @RequestMapping(value = "/search/{username}", method = RequestMethod.POST)
    List<User> getUsersByUsername(@PathVariable("username") String username) {
        return userDao.getUsersByUsername(username);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    List<User> getAllUsers() {
        return userDao.getUsersByUsername("");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Boolean createUser(HttpServletRequest request, @RequestBody UserSessionRequest userCreationRequest) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (userLoggedIn) {
            return false;
        }
        request.getSession().setAttribute("user", new User(userCreationRequest.getUsername(), false));
        return userDao.createUser(userCreationRequest);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    Boolean deleteUser(HttpServletRequest session, @PathVariable("username") String username) {
        User currentUser = (User) session.getSession().getAttribute("user");
        Boolean isUserOwner = currentUser.getUsername().equals(username);
        if (!isUserOwner && !currentUser.getAdmin()) {
            return false;
        }
        userDao.deleteUser(username);
        return true;
    }

}
