package org.minigur.site.service;

import org.minigur.site.Environment;
import org.minigur.site.models.Rating;
import org.minigur.site.models.RatingData;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;


/**
 * Rating DAO accesses rating information from the database
 */
public class RatingDAO {
    @Autowired
    Environment environment;
    /**
     * Get Rating gets the rating information for a specific imageID
     */
    public RatingData getRating(String imageID) {
        int downvotes = 0;
        int upvotes = 0;
        RatingData result = null;
        try (Connection connection = environment.getJdbcManager().connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(Rating.is_upvote) AS Downvotes " +
                    "FROM minigur.Rating, minigur.Image" +
                    "WHERE Image.filename = '?' AND Image.id = Rating.image_id AND Rating.is_upvote = 0;");
            statement.setString(1, imageID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                downvotes = resultSet.getInt("Downvotes");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = environment.getJdbcManager().connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(Rating.is_upvote) AS Upvotes " +
                    "FROM minigur.Rating, minigur.Image" +
                    "WHERE Image.filename = '?' AND Image.id = Rating.image_id AND Rating.is_upvote = 1;");
            statement.setString(1, imageID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                upvotes = resultSet.getInt("Upvotes");
            }
            result = new RatingData(upvotes, downvotes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Boolean postRating (Boolean is_upvote, String imageID, String userID) {
        try (Connection connection = environment.getJdbcManager().connect()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO minigur.Rating (user_id, image_id, is_upvote) VALUES (?, ?, ?);");
            statement.setString(1, userID);
            statement.setString(2, imageID);
            if (is_upvote) {
                statement.setInt(3, 1);
            } else {
                statement.setInt(3, 0);
            }
            statement.executeQuery();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}