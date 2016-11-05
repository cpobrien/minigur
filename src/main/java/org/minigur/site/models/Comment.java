package org.minigur.site.models;

import java.util.Date;

public class Comment {

    // TODO: not sure about the fields
    private String text;
    private User ownerUser;
    private int imageId;
    private Date postedDate;

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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(String text, User ownerUser, int imageId, Date postedDate) {
        this.text = text;
        this.ownerUser = ownerUser;
        this.imageId = imageId;
        this.postedDate = postedDate;
    }
}
