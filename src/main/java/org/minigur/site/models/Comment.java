package org.minigur.site.models;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Comment {



    // TODO: not sure about the fields
    private String text;
    private User ownerUser;
    private String imageId;
    private Date postedDate;
    private String commentID;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getCommentID() {return this.commentID;}

    public Comment(String text) {
        this.text = text;
    }

    public Comment(String text, User ownerUser, String imageId, Date postedDate, String commentID) {
        this.text = text;
        this.ownerUser = ownerUser;
        this.imageId = imageId;
        this.postedDate = postedDate;
        this.commentID = commentID;
    }
}
