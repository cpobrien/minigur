package org.minigur.site.components;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.minigur.site.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class UserManager {
    public UserManager() {
    }

    @Autowired
    UserDAO userDao;

    User getUser(@PathVariable("username") String username) {
        return new User(username, false);
    }

    Boolean createUser(HttpServletRequest request, @RequestBody UserSessionRequest userCreationRequest) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (userLoggedIn) {
            return false;
        }
        // TODO: Implement user creation
        return true;
    }

    Boolean deleteUser(HttpServletRequest session, @PathVariable("username") String username) {
        User currentUser = (User) session.getAttribute("user");
        Boolean isUserOwner = currentUser.getUsername().equals(username);
        if (!isUserOwner && !currentUser.getAdmin()) {
            return false;
        }
        return true;
    }
}
