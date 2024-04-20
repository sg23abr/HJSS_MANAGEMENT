package org.hjss.test;

import org.hjss.constants.Grade;
import org.hjss.exception.InvalidBooking;
import org.hjss.Main;
import org.hjss.models.SwimmingLesson;
import org.hjss.models.SwimmingSchool;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.junit.Assert.*;

public class SwimmingSchoolTest {
    private SwimmingSchool swimmingSchool = null;
    private List<SwimmingLesson> lessons = null;
    private String bookingId;
    private String reply;

    LocalDate date = LocalDate.now();
    @Before
    public void createInstanceOfWFC() {
        swimmingSchool = SwimmingSchool.getInstance();
    }

    @Test
    public void viewLessonsByDay() {
        String dayInput = "Monday";
        DayOfWeek day = DayOfWeek.valueOf(dayInput.toUpperCase());
        lessons = swimmingSchool.viewTimetable(day, 0, null);
        assertNotNull(lessons);

        for (SwimmingLesson lesson : lessons) {
            LocalDate lessonDate = lesson.getDate();
            assertEquals(lessonDate.getDayOfWeek(), DayOfWeek.MONDAY);
            if (lessonDate.compareTo(LocalDate.now()) < 0) {
                fail("Lesson date cannot be in past");
            }
        }

        Main.viewTimetable(lessons);
    }

    @Test
    public void viewLessonsByGrade() {
        int grade = 2;
        lessons = swimmingSchool.viewTimetable(null, grade, null);
        assertNotNull(lessons);

        for (SwimmingLesson lesson : lessons) {
            assertEquals(lesson.getGrade(), Grade.GRADE_2);
        }
        Main.viewTimetable(lessons);
    }

    @Test
    public void viewLessonsByCoachName() {
        String coachName = "Helen";
        lessons = swimmingSchool.viewTimetable(null, 0, coachName);
        assertNotNull(lessons);

        for (SwimmingLesson lesson : lessons) {
            assertEquals(lesson.getCoach().getName(), coachName);
        }
        Main.viewTimetable(lessons);
    }


    @Test
    public void bookLesson() {
        LocalDate bookingDate = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalTime time = LocalTime.parse("16:00");
        bookingId = swimmingSchool.bookLesson(time, bookingDate, "L15");
        assertNotNull(bookingId);
        System.out.println("Booking Id = "+bookingId);
    }


    @Test
    public void markBookingAttended() throws InvalidBooking {
        //booking id =
        reply = swimmingSchool.markBookingAttended("B4L4");
        System.out.println(reply);
    }

    @Test
    public void writeReview() {
        reply = swimmingSchool.provideReview("B4L4", 3);
        System.out.println("Reply for write review: "+reply);
    }

}

