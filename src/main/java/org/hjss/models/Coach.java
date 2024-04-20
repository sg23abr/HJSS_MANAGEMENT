package org.hjss.models;

import java.util.List;

public class Coach {
    private String name;
    private List<SwimmingLesson> swimmingLessons;

    public Coach() {
    }

    public Coach(String name, List<SwimmingLesson> swimmingLessons) {
        this.name = name;
        this.swimmingLessons = swimmingLessons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SwimmingLesson> getLessons() {
        return swimmingLessons;
    }

    public void setLessons(List<SwimmingLesson> swimmingLessons) {
        this.swimmingLessons = swimmingLessons;
    }

}
