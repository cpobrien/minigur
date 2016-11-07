package org.minigur.site.service;

import com.amazonaws.services.s3.model.*;
import org.minigur.site.Environment;
import org.minigur.site.models.Image;
import org.minigur.site.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ImageDAO {
    @Autowired
    Environment environment;

    @Autowired
    UserDAO userDAO;

    private static final Integer RANDOM_STRING_SIZE = 7;

    private String generateBase62(int size) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder("");
        for (Integer i = 0; i < size; i++) {
            Integer randomPosition = ThreadLocalRandom.current().nextInt(0, alphabet.length() - 1);
            builder.append(alphabet.charAt(randomPosition));
        }
        return builder.toString();
    }

    public Image findImage(String imageId) {
        try {
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("SELECT title, upload_time, username " +
                    "FROM minigur.Image, minigur.User WHERE User.id = Image.owner_user AND filename = ?;");
            statement.setString(1, imageId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            String imageUrl = imageId;
            String imageTitle = resultSet.getString("title");
            Date uploadTime = resultSet.getDate("upload_time");
            String username = resultSet.getString("username");
            connection.close();
            return new Image(imageUrl, imageTitle, uploadTime, new User(username, false));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Image> getLatestImages(Integer count) {
        List<Image> images = new ArrayList<>();
        try {
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement statement = connection.prepareStatement("SELECT filename, title, upload_time, username " +
                    "FROM minigur.Image, minigur.User " +
                    "WHERE User.id = Image.owner_user ORDER BY upload_time ASC LIMIT ?;");
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String filename = resultSet.getString("filename");
                String title = resultSet.getString("title");
                String username = resultSet.getString("username");
                Date uploadTime = resultSet.getDate("upload_time");
                Image image = new Image(filename, title, uploadTime, new User(username, false));
                images.add(image);
            }
            connection.close();
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public String uploadImage(MultipartFile image, String title, String username) {
        String randomString = generateBase62(RANDOM_STRING_SIZE);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            PutObjectRequest request = new PutObjectRequest("minigur",
                    String.format("%s.jpg", randomString),
                    image.getInputStream(),
                    metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            environment.getClient().putObject(request);
            Connection connection = environment.getJdbcManager().connect();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO minigur.Image (filename, owner_user, title) VALUES (?, ?, ?)");
            ps.setString(1, randomString);
            ps.setInt(2, userDAO.getUserID(username));
            ps.setString(3, title);
            ps.execute();
            connection.close();
            return randomString;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}