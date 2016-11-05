package org.minigur.site.service;

import org.minigur.site.Environment;
import org.minigur.site.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by DavidKorchinsky on 2016-11-04.
 */
public class CommentDAO {

    @Autowired
    Environment environment;

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

}
