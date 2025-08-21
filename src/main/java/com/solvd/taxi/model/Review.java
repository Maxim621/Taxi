package com.solvd.taxi.model;

public class Review {
    private int reviewId;
    private int rideId;
    private int rating;
    private String comment;

    public Review() {}

    public Review(int rideId, int rating, String comment) {
        this.rideId = rideId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", rideId=" + rideId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}