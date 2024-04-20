package org.hjss.models;

import org.hjss.constants.Rating;

import java.time.LocalDate;

public class Review {
    private Rating rating;
    private String learnerId;
    private int lessonType;
    private LocalDate lessonDate;
    private LocalDate reviewDate;
    private String description;

    public Review() {
    }

    public Review(Rating rating, String learnerId, int lessonType, LocalDate lessonDate, LocalDate reviewDate, String description) {
        this.rating = rating;
        this.learnerId = learnerId;
        this.lessonType = lessonType;
        this.lessonDate = lessonDate;
        this.reviewDate = reviewDate;
        this.description = description;
    }

    public String getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(String learnerId) {
        this.learnerId = learnerId;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getLessonType() {
        return lessonType;
    }

    public void setLessonType(int lessonType) {
        this.lessonType = lessonType;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
