package org.minigur.site.models;


public class UserStats {
    Integer imageCount;
    Integer commentCount;
    Integer upvotes;
    Integer downvotes;

    public UserStats(Integer imageCount, Integer commentCount, Integer upvotes, Integer downvotes) {
        this.imageCount = imageCount;
        this.commentCount = commentCount;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public Integer getImageCount() {

        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }
}
