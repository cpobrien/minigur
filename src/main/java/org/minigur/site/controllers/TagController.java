package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Image;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {
    /**
     * Gets a list of images that have the given tag.
     * @param tagId the name of the tag
     * @return a list of images associated with the tag.
     */
    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    List<Image> getTaggedImages(@PathVariable("tagId") String tagId) {
        return null;
    }

    /**
     * Tags an image the image Id. Assumes user is logged in.
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param tagId   the name of the tag
     * @return a list of images associated with the tag.
     */
    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.POST)
    Boolean tagImage(HttpServletRequest request, @PathVariable("tagId") String tagId) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (!userLoggedIn) {
            return null;
        }
        return true;
    }
}
