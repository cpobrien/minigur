package org.minigur.site.service;

import org.minigur.site.controllers.UserSessionController;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.minigur.site.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public Boolean checkPassword(UserSessionRequest request) {
        String username = request.getUsername();
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        try {
            Connection c = environment.getJdbcManager().connect();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM minigur.User WHERE login = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, encodedPassword);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean createUser(UserSessionRequest request) {
        String username = request.getUsername();
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        try {
            Connection c = environment.getJdbcManager().connect();
            PreparedStatement ps = c.prepareStatement("INSERT INTO minigur.User VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, encodedPassword);
            ps.setBoolean(3, false);
            ps.execute();
            c.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
