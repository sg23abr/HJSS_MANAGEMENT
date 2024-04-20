package org.hjss.models;

import org.hjss.constants.Gender;
import org.hjss.constants.Grade;

import java.util.List;

public class Learner {

    private String id;
    private String name;
    private Gender gender;
    private int age;
    private String emergencyContact;
    private Grade currentGrade;
    private List<Booking> bookings;

    public Learner() {
    }

    public Learner(String id, String name, Gender gender, int age, String emergencyContact, Grade currentGrade, List<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.currentGrade = currentGrade;
        this.bookings = bookings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Grade getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(Grade currentGrade) {
        this.currentGrade = currentGrade;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
