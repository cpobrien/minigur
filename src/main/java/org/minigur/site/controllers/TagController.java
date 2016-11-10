package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Image;
import org.minigur.site.service.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagDAO tagDAO;
    /**
     * Gets a list of images that have the given tag.
     * @param tagId the name of the tag
     * @return a list of images associated with the tag.
     */
    @RequestMapping(value = "{tagId}", method = RequestMethod.GET)
    List<Image> getTaggedImages(@PathVariable("tagId") String tagId) {
        return null;
    }

    /**
     * Tags an image the image Id. Assumes user is logged in.
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param imageId The image id to tag
     * @param tag   the name of the tag
     * @return a list of images associated with the tag.
     */
    @RequestMapping(value = "{imageId}", method = RequestMethod.POST)
    String tagImage(HttpServletRequest request, @PathVariable("imageId") String imageId, @RequestParam("tag") String tag) {
        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            return "redirect:";
        }
        System.out.printf("Tagging image %s with id %s\n", imageId, tag);
        tagDAO.addTag(imageId, tag);
        return String.format("redirect:/%s", imageId);
    }
}
