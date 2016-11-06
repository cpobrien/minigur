package org.minigur.site.models;


import org.springframework.web.multipart.MultipartFile;

public class ImageUploadRequest {
    private MultipartFile image;
    private String title;

    public ImageUploadRequest() {
    }

    public ImageUploadRequest(MultipartFile image, String title) {
        this.image = image;
        this.title = title;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
