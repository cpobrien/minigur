package org.minigur.site.service;

import org.minigur.site.Environment;
import org.minigur.site.models.Comment;
import org.minigur.site.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommentDAO {

    @Autowired
    Environment environment;

    @Autowired
    UserDAO userDAO;

    public List<Comment> getComments(String imageID) {
        List<Comment> comments = new ArrayList<>();
        try(Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM minigur.Comment WHERE image_id = ? ORDER BY post_time DESC");
            ps.setString(1, imageID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(rs.getString("text")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return comments;
    }

    public Comment getComment(String commentID) {
        Comment comment;
        String query = "SELECT * FROM minigur.Comment comm, minigur.User u, WHERE comm.user_id = u.id AND comm.id = ?";
        try(Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, commentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("u.username"), rs.getBoolean("u.is_admin"));
                String text = rs.getString("comm.text");
                int imageId = rs.getInt("comm.image_id");
                Date date = rs.getDate("comm.post_time");
                return new Comment(text, user, imageId, date);
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addComment(Comment comment) {

        try(Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO minigur.Comment (user_id, image_id, text) VALUES (?, ?, ?)");

            int userId = userDAO.getUserID(comment.getOwnerUser().getUsername());
            if (userId == -1) {
                return false;
            }
            ps.setInt(1, userId);
            ps.setInt(2, comment.getImageId());
            ps.setString(3, comment.getText());
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteComment(int commentId) {
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM minigur.Comment WHERE id = ?");
            ps.setInt(1, commentId);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
