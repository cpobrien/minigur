package org.minigur.site.models;

public class RatingData {
    Integer numberOfUpvotes;
    Integer numberOfDownvotes;

    public RatingData(Integer numberOfUpvotes, Integer numberOfDownvotes) {
        this.numberOfUpvotes = numberOfUpvotes;
        this.numberOfDownvotes = numberOfDownvotes;
    }

    public Integer getNumberOfUpvotes() {
        return numberOfUpvotes;
    }

    public void setNumberOfUpvotes(Integer numberOfUpvotes) {
        this.numberOfUpvotes = numberOfUpvotes;
    }

    public Integer getNumberOfDownvotes() {
        return numberOfDownvotes;
    }

    public void setNumberOfDownvotes(Integer numberOfDownvotes) {
        this.numberOfDownvotes = numberOfDownvotes;
    }
}
