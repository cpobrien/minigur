package org.minigur.site.models;

public class Image {
    String imageUrl;
    String imageTitle;
    User imageOwner;

    public Image(String imageUrl, String imageTitle, User imageOwner) {
        this.imageUrl = imageUrl;
        this.imageTitle = imageTitle;
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
}
