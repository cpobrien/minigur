package org.minigur.site.models;

import java.sql.Date;

public class Image {
    String imageUrl;
    String imageTitle;
    Date uploadDate;
    User imageOwner;

    public Image() {
    }

    public Image(String imageUrl, String imageTitle, Date uploadDate, User imageOwner) {
        this.imageUrl = imageUrl;
        this.imageTitle = imageTitle;
        this.uploadDate = uploadDate;
        this.imageOwner = imageOwner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public User getImageOwner() {
        return imageOwner;
    }

    public void setImageOwner(User imageOwner) {
        this.imageOwner = imageOwner;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
