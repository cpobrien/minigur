package org.minigur.site.service;
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
        try (Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement("SELECT is_admin FROM minigur.User WHERE login = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Boolean isAdmin = rs.getBoolean("is_admin");
                return new User(username, isAdmin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkPassword(UserSessionRequest request) {
        String username = request.getUsername();
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        try (Connection c = environment.getJdbcManager().connect()){
            //Validate login
            PreparedStatement ps = c.prepareStatement("SELECT * FROM minigur.UserCredentials WHERE login = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, encodedPassword);
            ResultSet rsLogin = ps.executeQuery();

            if (rsLogin.next()) {
                //Get user and return
                ps = c.prepareStatement("SELECT * FROM minigur.User WHERE login = ?");
                ps.setString(1, username);
                ResultSet rsUser = ps.executeQuery();

                return rsUser.next();
            } else {
                //Otherwise return
                return rsLogin.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean createUser(UserSessionRequest request) {
        String username = request.getUsername();
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        try (Connection c = environment.getJdbcManager().connect()){
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO minigur.UserCredentials(username, password) VALUES (?, ?)")) {
                ps.setString(1, username);
                ps.setString(2, encodedPassword);
                ps.execute();
            }
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO minigur.User(username, is_admin) VALUES (?, ?)")) {
                ps.setString(1, username);
                ps.setBoolean(2, false);
                ps.execute();
            }

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean deleteUser(UserSessionRequest request) {
        String username = request.getUsername();
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM minigur.User WHERE username = ?");
            ps.setString(1, username);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
