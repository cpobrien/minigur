package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.User;
import org.minigur.site.service.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
//    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
//    List<Image> getTaggedImages(HttpServletRequest request, @PathVariable("tagId") String tagId) {
//        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
//        if (!userLoggedIn) {
//            return null;
//        }
//        System.out.println(imageDAO.getImagesByTag(tagId));
//        return imageDAO.getImagesByTag(tagId);
//    }


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
        User user = (User) request.getSession().getAttribute("user");
        tagDAO.addTag(imageId, tag, user.getUsername());
        return String.format("redirect:/%s", imageId);
    }


    @RequestMapping(value = "{tagName}", method = RequestMethod.DELETE)
    @ResponseBody
    Boolean deleteTag(HttpServletRequest request, @PathVariable("tagName") String tagName, @RequestBody String imageId) {
        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            return false;
        }
        tagDAO.deleteTag(tagName, imageId);
        return true;
    }
}
