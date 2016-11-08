package org.minigur.site.models;

public class CommentPostRequest {
    String text;

    public CommentPostRequest(String text) {
        this.text = text;
    }

    public CommentPostRequest() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
