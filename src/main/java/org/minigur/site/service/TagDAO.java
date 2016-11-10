package org.minigur.site.service;

import org.minigur.site.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagDAO {
    @Autowired
    Environment environment;

    public Boolean addTag(String image, String tag) {
        try {
            if (getTagId(tag) == null) {
                createTagId(tag);
            }
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO minigur.TagRelations (image_id, tag_id) VALUES ((SELECT id FROM minigur.Image WHERE filename = ?), (SELECT id FROM minigur.Tag WHERE name = ?))");
            statement.setString(1, image);
            statement.setString(2, tag);
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
}
