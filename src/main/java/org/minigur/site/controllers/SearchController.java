package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Image;
import org.minigur.site.models.SearchRequest;
import org.minigur.site.service.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by oem on 09/11/16.
 */
@Controller
public class SearchController {
    @Autowired
    SearchDAO searchDao;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    Boolean search(HttpServletRequest request, @RequestBody SearchRequest searchRequest, Model model) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (userLoggedIn) {
            return false;
        }
        List<Image> searchResults = searchDao.searchImages(searchRequest);
        return true;
    }




}

