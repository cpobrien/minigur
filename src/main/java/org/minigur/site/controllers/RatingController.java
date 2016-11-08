package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Rating;
import org.minigur.site.models.RatingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {
//    @Autowired
//    RatingDAO ratingDAO;
    /**
     * Posts a rating to an image. Requires a user to be logged in.
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param imageId The image the rating is associated with
     * @param rating Json of the form {rating: true|false} serialized into a Rating
     * @return whether the rating was successfully uploaded or not.
     */
    @RequestMapping(value = "/{imageId}/rating", method = RequestMethod.POST)
    Boolean postRating(HttpServletRequest request, @PathVariable("imageId") String imageId, @RequestBody Rating rating) {
        Boolean userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            return false;
        }
//        ratingDAO.postRating;
        return true;
    }

    /**
     * Gets rating information for an image
     * @param imageId the image the ratings are associated with.
     * @return rating information
     */
    @RequestMapping(value = "/{imageId}/rating", method = RequestMethod.GET)
    RatingData getRatingData(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * Deletes a user's rating associated with an image.
     * Assumes:
     *  1. A user is logged on
     *  2. They have rated the image
     * @param request The HTTP Request. Contains information about the current user logged on.
     * @param imageId The image the rating is associated with.
     * @return Whether or not the rating was successfully deleted.
     */
    @RequestMapping(value = "/{imageId}/rating", method = RequestMethod.DELETE)
    Boolean deleteRating(HttpServletRequest request, @PathVariable("imageId") String imageId) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (!userLoggedIn) {
            return false;
        }
        return true;
    }
}
