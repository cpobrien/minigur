package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Image;
import org.minigur.site.models.ImageUploadRequest;
import org.minigur.site.models.User;
import org.minigur.site.service.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    @Autowired
    ImageDAO imageDAO;
    /**
     * Uploads an image. Requires a user to be logged in.
     *
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param file The file to be uploaded
     * @param title The title of the file
     * @return The string of the URL uploaded to, or null if an invalid request.
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    String postImage(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("title") String title) {
        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            return null;
        }
        User user = (User) request.getSession().getAttribute("user");
        String imageLocation = imageDAO.uploadImage(file, title, user.getUsername());
        return String.format("redirect:%s", imageLocation);
    }

    /**
     * Gets information about an image
     *
     * @param imageId the id associated with an image
     * @return image information
     */
    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET)
    Image getImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * Deletes an image assuming the following:
     * 1. The user is logged in.
     * 2. The user owns the image
     * OR
     * 3. The user is an admin
     *
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param imageId the id associated with an image
     * @return
     */
    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.DELETE)
    String deleteImage(HttpServletRequest request, @PathVariable("imageId") String imageId) {
        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            return "redirect:";
        }
        User user = (User) request.getSession().getAttribute("user");
        Image image = imageDAO.findImage(imageId);
        if (!image.getImageOwner().getUsername().equals(user.getUsername()) && !user.getAdmin()) {
            return "redirect:/"+imageId;
        }

        imageDAO.deleteImage(imageId);
        return "redirect:";

    }
}
