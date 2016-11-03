package org.minigur.site.controllers;

import org.minigur.site.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class SiteRenderController {
    @Autowired
    Environment environment;

    @GetMapping("/")
    String home() {
        print();
        return "home";
    }

    public void print() {
        try {
            ResultSet resultSet = environment
                    .getJdbcManager()
                    .connect()
                    .createStatement()
                    .executeQuery("SELECT * FROM minigur.Image");
            while (resultSet.next()) {
                System.out.printf("The image with url %s, uploaded at time %s, was uploaded by the user with id %s.\n",
                        resultSet.getString("filename"),
                        resultSet.getString("upload_time"),
                        resultSet.getString("owner_user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
