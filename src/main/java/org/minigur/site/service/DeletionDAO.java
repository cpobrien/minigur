package org.minigur.site.service;

import org.minigur.site.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DeletionDAO {
    @Autowired
    Environment environment;

    // Delete Comment. takes in a comment_id found in the comment object (randomly generated String)
    public boolean deleteComment(String commentId) {
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM minigur.Comment WHERE comment_id = ?");
            ps.setString(1, commentId);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean deleteUser (String Username) {
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("delete from minigur.UserCredentials where username=?;");
            ps.setString(1, Username);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // deletes image and tag_relations (but what about tags?)
    public Boolean deleteImage(String imageID) {
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM minigur.Image WHERE filename = ?");
            ps.setString(1, imageID);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // deletes the tag relation off of the image
    public Boolean deleteTag(String tag, String imgURL) {
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM minigur.TagRelations " +
                    "WHERE tag_id IN (SELECT id FROM minigur.Tag WHERE name = ?) " +
                    "AND image_id IN (SELECT id FROM minigur.Image WHERE filename = ?);");
            ps.setString(1, tag);
            ps.setString(2, imgURL);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
