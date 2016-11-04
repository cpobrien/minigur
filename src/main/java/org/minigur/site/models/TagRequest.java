package org.minigur.site.models;

public class TagRequest {
    String imageId;

    public TagRequest(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
