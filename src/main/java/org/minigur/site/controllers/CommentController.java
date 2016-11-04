package org.minigur.site.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.minigur.site.models.Comment;
import org.minigur.site.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    /**
     * Gets all comments for an image
     * @param imageId the image the comments are associated with.
     * @return a list of comments
     */
    @RequestMapping(value = "/{imageId}/comment", method = RequestMethod.GET)
    List<Comment> getComments(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * Uploads a comment to the image, assuming there is a user logged in.
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param imageId The image the comment is associated with
     * @param comment Json of the form {comment: comment} serialized into a Comment
     * @return whether the comment was successfully uploaded or not.
     */
    @RequestMapping(value = "/{imageId}/comment", method = RequestMethod.POST)
    Boolean postComment(HttpServletRequest request, @PathVariable("imageId") String imageId, @RequestBody Comment comment) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (!userLoggedIn) {
            return false;
        }
        return false;
    }

    /**
     * Gets information about the current comment.
     * @param imageId the image the comment is associated with
     * @param commentId the id of the comment
     * @return comment information
     */
    @RequestMapping(value = "/{imageId}/comment/{commentId}", method = RequestMethod.GET)
    Comment getComment(@PathVariable("imageId") String imageId, @PathVariable("commentId") String commentId) {
        return null;
    }

    /**
     * Deletes the comment if:
     *  1. The user is an admin
     *  2. The user owns the image
     *  3. The user owns the comment
     * @param request The HTTP Request. Contains information about the current user logged in.
     * @param imageId The image the comment is associated with.
     * @param commentId The id of the comment
     * @return whether or not the comment was successfully deleted.
     */
    @RequestMapping(value = "/{imageId}/comment/{commentId}", method = RequestMethod.DELETE)
    Boolean deleteComment(HttpServletRequest request,
                          @PathVariable("imageId") String imageId,
                          @PathVariable("commentId") String commentId) {
        Boolean userLoggedIn = request.getAttribute("user") != null;
        if (!userLoggedIn) {
            return false;
        }
        User user = (User) request.getAttribute("user");
        return true;
    }
}
