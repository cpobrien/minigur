package org.minigur.site.service;
import org.minigur.site.models.Image;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.minigur.site.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    @Autowired
    Environment environment;

    @Autowired
    RatingDAO ratingDAO;

    @Autowired
    ImageDAO imageDAO;

    public User getUser(String username) {
        try (Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement("SELECT is_admin FROM minigur.User WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Boolean isAdmin = rs.getBoolean("is_admin");
                ps.close();
                return new User(username, isAdmin);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkPassword(UserSessionRequest request) {
        String username = request.getUsername();
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("SELECT password FROM minigur.UserCredentials WHERE username = ?");
            ps.setString(1, username);
            ResultSet rsLogin = ps.executeQuery();
            if (!rsLogin.next()) {
                return false;
            }
            return new BCryptPasswordEncoder().matches(request.getPassword(), rsLogin.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // http://stackoverflow.com/questions/22000915/preparedstatement-how-to-insert-data-into-multiple-tables-using-jdbc
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

    public int getUserID(String username) {
        try (Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement("SELECT id FROM minigur.User WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    // deletes the user provided along with their posts, ratings and tags.
    public Boolean deleteUser (String username) {
        int userID = getUserID(username);
        // Delete UserCredentials, User
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE from minigur.UserCredentials where username=?;");
            ps.setString(1, username);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        // Delete Comments
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE from minigur.Comment where user_id=?;");
            ps.setInt(1, userID);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Delete ALL ratings
        ratingDAO.deleteRating(username);

        //Delete ALL images from the user
        List<Image> imgURLs = imageDAO.getUserImages(username);
        for(Image i : imgURLs) {
            imageDAO.deleteImage(i.getImageUrl());
        }

        // Deletes all the tags associated with the user

        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE from minigur.TagRelations where user_id=?;");
            ps.setInt(1, userID);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<User> getUsersByUsername(String username) {
        List<User> users = new ArrayList<>();
        try (Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM minigur.User WHERE username LIKE ?;");
            ps.setString(1, "%" + username + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getBoolean("is_admin"));
                users.add(user);
            }
        }
        catch (SQLException e) {
            return null;
        }
        return users;
    }
}
