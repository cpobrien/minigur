package org.minigur.site.models;

public class RatingData {
    Double ratingAverage;
    Integer numberOfUpvotes;
    Integer numberOfDownvotes;

    public RatingData(Double ratingAverage, Integer numberOfUpvotes, Integer numberOfDownvotes) {
        this.ratingAverage = ratingAverage;
        this.numberOfUpvotes = numberOfUpvotes;
        this.numberOfDownvotes = numberOfDownvotes;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
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
