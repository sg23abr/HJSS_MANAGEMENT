package org.hjss.models;


import java.time.LocalDate;

public class Booking {
    private String bookingID;
    private LocalDate bookingDate;
    private Learner learner;
    private SwimmingLesson swimmingLesson;
    private String bookingStatus;
    private Review review;

    public Booking() {
    }

    public Booking(String bookingID, LocalDate bookingDate, Learner learner, SwimmingLesson swimmingLesson, String bookingStatus, Review review) {
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.learner = learner;
        this.swimmingLesson = swimmingLesson;
        this.bookingStatus = bookingStatus;
        this.review = review;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public SwimmingLesson getLesson() {
        return swimmingLesson;
    }

    public void setLesson(SwimmingLesson swimmingLesson) {
        this.swimmingLesson = swimmingLesson;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
