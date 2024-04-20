package org.hjss.models;

import org.hjss.constants.Grade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SwimmingLesson {

    private Grade grade;
    private LocalDate date;
    private LocalTime timeSlot;
    private Coach coach;
    private List<Learner> learners;
    private int capacity = 4;
    private List<Review> reviews;
    private int availableSlots = 4;

    public SwimmingLesson() {
    }

    public SwimmingLesson(Grade grade, LocalDate date, LocalTime timeSlot, Coach coach, List<Learner> learners, int capacity, List<Review> reviews, int availableSlots) {
        this.grade = grade;
        this.date = date;
        this.timeSlot = timeSlot;
        this.coach = coach;
        this.learners = learners;
        this.capacity = capacity;
        this.reviews = reviews;
        this.availableSlots = availableSlots;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<Learner> getLearners() {
        return learners;
    }

    public void setLearners(List<Learner> learners) {
        this.learners = learners;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }
}
