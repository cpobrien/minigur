package org.minigur.site.service;
import org.minigur.site.models.Image;
import org.minigur.site.models.User;
import org.minigur.site.models.UserSessionRequest;
import org.minigur.site.models.UserStats;
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

        //Delete ALL images from the user
        List<Image> imgURLs = imageDAO.getUserImages(username);
        for(Image i : imgURLs) {
            imageDAO.deleteImage(i.getImageUrl());
        }

        // Delete UserCredentials, User, Images, tags, comments (due to cascading)
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE from minigur.UserCredentials where username=?;");
            ps.setString(1, username);
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

    public UserStats getUserStats(String username) {
        Integer userId = getUserID(username);
        Integer comments = 0;
        Integer upvotes = 0;
        Integer downvotes = 0;
        try (Connection c = environment.getJdbcManager().connect()) {

            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM minigur.Rating WHERE Rating.user_id = ? AND Rating.is_upvote = ?")) {
                ps.setInt(1, userId);
                ps.setBoolean(2, true);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    upvotes = rs.getInt(1);
                }
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM minigur.Rating WHERE Rating.user_id = ? AND Rating.is_upvote = ?")) {
                ps.setInt(1, userId);
                ps.setBoolean(2, false);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    downvotes = rs.getInt(1);
                }
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM minigur.Comment WHERE user_id = ?")) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    comments = rs.getInt(1);
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new UserStats(0, comments, upvotes, downvotes);
    }
}
