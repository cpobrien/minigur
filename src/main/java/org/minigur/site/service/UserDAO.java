package org.minigur.site.service;

import org.minigur.site.controllers.UserSessionController;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.minigur.site.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDAO {
    @Autowired
    Environment environment;

    public User getUser(String username) {
        try {
            Connection c = environment.getJdbcManager().connect();
            PreparedStatement ps = c.prepareStatement("SELECT is_admin FROM minigur.User WHERE login = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Boolean isAdmin = rs.getBoolean("is_admin");
                return new User(username, isAdmin);
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
