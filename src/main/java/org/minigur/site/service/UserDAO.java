package org.minigur.site.service;

import org.minigur.site.controllers.UserSessionController;
import org.minigur.site.models.UserSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.minigur.site.Environment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {

    @Autowired
    Environment environment;

    public boolean createUser(UserSessionRequest userCreationRequest) {
        try {
            Connection c = environment.getJdbcManager().connect();
            PreparedStatement ps = c.prepareStatement("SELECT User.id FROM User WHERE User.login = ?");
            ps.setString(1, userCreationRequest.getUsername());
            ResultSet rs = ps.executeQuery();
            c.close();
            if (rs.next()) {
                // user exists

            }
            else {
                // user with such username does not exist

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
