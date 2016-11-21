package org.minigur.site.service;

import org.minigur.site.Environment;
import org.minigur.site.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TagDAO {
    @Autowired
    Environment environment;

    @Autowired
    UserDAO userDAO;

    private Tag resultSetToTag(ResultSet resultSet) throws SQLException {
        String tag = resultSet.getString("tag");
        Integer count = resultSet.getInt("count");
        return new Tag(tag, count);
    }

    public List<Tag> getTags(String imageId) {
        List<Tag> tags = new ArrayList<>();
        try {
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("SELECT name tag, COUNT(name) count FROM minigur.Tag t, minigur.Image i, minigur.TagRelations r WHERE t.id = r.tag_id AND i.filename = ? AND i.id = r.image_id GROUP BY name ORDER BY count DESC;");
            statement.setString(1, imageId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tags.add(resultSetToTag(resultSet));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }


    public Boolean addTag(String image, String tag, String userID) {
        try {
            if (getTagId(tag) == null) {
                createTagId(tag);
            }
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO minigur.TagRelations (image_id, tag_id, user_id) VALUES ((SELECT id FROM minigur.Image WHERE filename = ?), (SELECT id FROM minigur.Tag WHERE name = ?), ?)");
            statement.setString(1, image);
            statement.setString(2, tag);
            statement.setInt(3, userDAO.getUserID(userID));
            statement.execute();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean createTagId(String tag) {
        try {
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO minigur.Tag (name) VALUES (?)");
            statement.setString(1, tag);
            statement.execute();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer getTagId(String tag) {
        try {
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM minigur.Tag WHERE name = ?;");
            statement.setString(1, tag);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Integer id = resultSet.getInt("id");
            connection.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // deletes the tag relation off of the image
    public Boolean deleteTag(String tag, String imgURL) {
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE FROM minigur.TagRelations " +
                    "WHERE tag_id IN (SELECT id FROM minigur.Tag t WHERE t.name = ?) " +
                    "AND image_id IN (SELECT id FROM minigur.Image i WHERE i.filename = ?);");
            ps.setString(1, tag);
            ps.setString(2, imgURL);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // delete all unused tags from the Tag table
        try (Connection c = environment.getJdbcManager().connect()){
            PreparedStatement ps = c.prepareStatement("DELETE from minigur.Tag " +
                    "WHERE id NOT IN (SELECT tag_id from minigur.TagRelations);");
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
