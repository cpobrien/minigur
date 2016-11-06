package org.minigur.site.service;

import com.amazonaws.services.s3.model.*;
import org.minigur.site.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            return randomString;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
