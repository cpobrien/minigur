package org.minigur.site.service;

import org.minigur.site.Environment;
import org.minigur.site.models.Image;
import org.minigur.site.models.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class SearchDAO {
    @Autowired
    Environment environment;

    public List<Image> searchImages(SearchRequest request) {
        List<Image> images = new ArrayList<>();
        String searchString = request.getSearchString().toLowerCase();
        String searchUsername = request.getSearchUsername() == Boolean.TRUE ? "SELECT i.id FROM minigur.Image i, minigur.User u" +
                " WHERE i.owner_user = u.id AND LOWER(u.username) LIKE \'%" +  searchString + "%\'" : null;
        String searchTag = request.getSearchTag() == Boolean.TRUE ? "SELECT i.id FROM minigur.Image i, minigur.TagRelations tr, minigur.Tag t" +
                " WHERE t.id = tr.id AND i.id = tr.id AND LOWER(t.name) LIKE \'%" + searchString + "%\'" : null;
        String searchComment = request.getSearchTag() == Boolean.TRUE ? "SELECT c.image_id FROM minigur.Comment c" +
                " WHERE LOWER(c.text) LIKE \'%" + searchString + "%\'" : null;
        String searchTitle = request.getSearchTag() == Boolean.TRUE ? "SELECT i.id FROM minigur.Image i" +
                " WHERE LOWER(i.title) LIKE \'%" + searchString + "%\'" : null;
        String query = "SELECT * from minigur.Image i WHERE i.id IN (";
        List<String> searchQueries = new ArrayList<>();
        if (searchUsername != null) {
            searchQueries.add(searchUsername);
        }
        if (searchComment != null) {
            searchQueries.add(searchComment);
        }
        if (searchTitle != null) {
            searchQueries.add(searchTitle);
        }
        if (searchTag != null) {
            searchQueries.add(searchTag);
        }
        for (int i = 0; i < searchQueries.size(); i++) {
            if (i == searchQueries.size() - 1) {
                query += searchQueries.get(i) + ");";
            } else {
                query += searchQueries.get(i) + " UNION ";
            }
        }

        try (Connection c = environment.getJdbcManager().connect()) {
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // TODO: complete if you decide to leave this mess
                String filename = rs.getString("i.filename");
                String title = rs.getString("i.title");
                Date date = rs.getDate("i.upload_time");

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
