package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Image;
import org.minigur.site.models.ImageUploadRequest;
import org.minigur.site.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {
    /**
     * Uploads an image. Requires a user to be logged in.
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param uploadRequest Json of the form {image: image, title: title} serialized into an ImageUploadRequest
     * @return The string of the URL uploaded to, or null if an invalid request.
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    String postImage(HttpServletRequest request, @RequestBody ImageUploadRequest uploadRequest) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (!userLoggedIn) {
            return null;
        }
        return null;
    }

    /**
     * Gets information about an image
     * @param imageId the id associated with an image
     * @return image information
     */
    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET)
    Image getImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * Deletes an image assuming the following:
     *  1. The user is logged in.
     *  2. The user owns the image
     * OR
     *  3. The user is an admin
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param imageId the id associated with an image
     * @return
     */
    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.DELETE)
    Boolean deleteImage(HttpServletRequest request, @PathVariable("imageId") String imageId) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (!userLoggedIn) {
            return null;
        }
        User user = (User) request.getAttribute("user");
        return false;
    }
}
