package org.hjss.utils;

import org.hjss.constants.BookingStatus;
import org.hjss.constants.Gender;
import org.hjss.constants.Grade;
import org.hjss.constants.Rating;
import org.hjss.models.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

/**
 * Utility class for initializing swimming school data.
 */
public class SwimmingSchoolDataInitializer {

    /**
     * Initializes the swimming school data.
     *
     * @param swimmingSchool The swimming school instance to initialize.
     */
    public static void initializeData(SwimmingSchool swimmingSchool) {
        generateTimetable(swimmingSchool, 4);
        generateLearners(swimmingSchool);
        generateBookings(swimmingSchool);
        generateReviews(swimmingSchool);
    }

    /**
     * Generates the swimming lesson timetable for a specified number of weeks.
     *
     * @param swimmingSchool The swimming school instance.
     * @param numWeeks       The number of weeks to generate timetable for.
     */
    private static void generateTimetable(SwimmingSchool swimmingSchool, int numWeeks) {

        Coach c1 = new Coach("Shivani", null);
        Coach c2 = new Coach("John", null);
        Coach c3 = new Coach("Helen", null);
        Coach c4 = new Coach("Alice", null);

        swimmingSchool.getCoaches().add(c1);
        swimmingSchool.getCoaches().add(c2);
        swimmingSchool.getCoaches().add(c3);
        swimmingSchool.getCoaches().add(c4);

        LocalDate currentDate = LocalDate.now();

        LocalDate previousMonday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate previousWednesday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));
        LocalDate previousFriday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        LocalDate previousSaturday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));


        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, previousMonday, LocalTime.of(16, 0), c1, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_2, previousMonday, LocalTime.of(17, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_3, previousMonday, LocalTime.of(18, 0), c3, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, previousWednesday, LocalTime.of(16, 0), c1, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_2, previousWednesday, LocalTime.of(17, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_3, previousWednesday, LocalTime.of(18, 0), c3, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, previousFriday, LocalTime.of(16, 0), c1, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_2, previousFriday, LocalTime.of(17, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_3, previousFriday, LocalTime.of(18, 0), c3, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, previousSaturday, LocalTime.of(14, 0), c1, new ArrayList<>(), 4, new ArrayList<>(), 4));
        swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_2, previousSaturday, LocalTime.of(15, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));

        for (int i = 0; i < numWeeks; i++) {

            LocalDate nextMonday = currentDate.plusWeeks(i).with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            LocalDate nextWednesday = currentDate.plusWeeks(i).with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
            LocalDate nextFriday = currentDate.plusWeeks(i).with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            LocalDate nextSaturday = currentDate.plusWeeks(i).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, nextMonday, LocalTime.of(16, 0), c1, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_2, nextMonday, LocalTime.of(17, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_3, nextMonday, LocalTime.of(18, 0), c3, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_4, nextWednesday, LocalTime.of(16, 0), c3, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_5, nextWednesday, LocalTime.of(17, 0), c1, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, nextWednesday, LocalTime.of(18, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_2, nextFriday, LocalTime.of(16, 0), c4, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_3, nextFriday, LocalTime.of(17, 0), c3, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_4, nextFriday, LocalTime.of(18, 0), c4, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_5, nextSaturday, LocalTime.of(14, 0), c2, new ArrayList<>(), 4, new ArrayList<>(), 4));
            swimmingSchool.getTimetable().add(new SwimmingLesson(Grade.GRADE_1, nextSaturday, LocalTime.of(15, 0), c4, new ArrayList<>(), 4, new ArrayList<>(), 4));

        }
    }

    /**
     * Generates learners and adds them to the swimming school instance.
     *
     * @param swimmingSchool The swimming school instance.
     */
    private static void generateLearners(SwimmingSchool swimmingSchool) {

        Learner l1 = new Learner("L1", "John Doe", Gender.MALE, 4, "Emergency Contact 1", Grade.GRADE_1, new ArrayList<>());
        Learner l2 = new Learner("L2", "Jane Smith", Gender.MALE, 5, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l3 = new Learner("L3", "Emily Johnson", Gender.FEMALE, 6, "Emergency Contact 2", Grade.GRADE_2, new ArrayList<>());
        Learner l4 = new Learner("L4", "Michael Williams", Gender.MALE, 7, "Emergency Contact 2", Grade.GRADE_4, new ArrayList<>());
        Learner l5 = new Learner("L5", "Sarah Brown", Gender.FEMALE, 8, "Emergency Contact 2", Grade.GRADE_5, new ArrayList<>());
        Learner l6 = new Learner("L6", "David Jones", Gender.MALE, 9, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l7 = new Learner("L7", "Jessica Davis", Gender.FEMALE, 10, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l8 = new Learner("L8", "Daniel Miller", Gender.MALE, 11, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l9 = new Learner("L9", "Amanda Wilson", Gender.FEMALE, 10, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l10 = new Learner("L10", "James Taylor", Gender.MALE, 9, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l11 = new Learner("L11", "Ashley Anderson", Gender.MALE, 8, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l12 = new Learner("L12", "Robert Martinez", Gender.MALE, 7, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l13 = new Learner("L13", "Jennifer Lee", Gender.FEMALE, 6, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l14 = new Learner("L14", "William Clark", Gender.MALE, 5, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());
        Learner l15 = new Learner("L15", "Christopher Hall", Gender.FEMALE, 4, "Emergency Contact 2", Grade.GRADE_1, new ArrayList<>());

        swimmingSchool.getLearners().add(l1);
        swimmingSchool.getLearners().add(l2);
        swimmingSchool.getLearners().add(l3);
        swimmingSchool.getLearners().add(l4);
        swimmingSchool.getLearners().add(l5);
        swimmingSchool.getLearners().add(l6);
        swimmingSchool.getLearners().add(l7);
        swimmingSchool.getLearners().add(l8);
        swimmingSchool.getLearners().add(l9);
        swimmingSchool.getLearners().add(l10);
        swimmingSchool.getLearners().add(l11);
        swimmingSchool.getLearners().add(l12);
        swimmingSchool.getLearners().add(l13);
        swimmingSchool.getLearners().add(l14);
        swimmingSchool.getLearners().add(l15);

    }

    /**
     * Generates bookings for learners based on the timetable.
     *
     * @param swimmingSchool The swimming school instance.
     */
    private static void generateBookings(SwimmingSchool swimmingSchool) {

        // Choose specific lessons from the timetable for bookings
        SwimmingLesson swimmingLesson1 = swimmingSchool.getTimetable().get(0);
        SwimmingLesson swimmingLesson2 = swimmingSchool.getTimetable().get(1);
        SwimmingLesson swimmingLesson3 = swimmingSchool.getTimetable().get(2);
        SwimmingLesson swimmingLesson4 = swimmingSchool.getTimetable().get(3);
        SwimmingLesson swimmingLesson5 = swimmingSchool.getTimetable().get(4);

        Learner learner1 = swimmingSchool.getLearnerByLearnerId("L1");
        Learner learner2 = swimmingSchool.getLearnerByLearnerId("L2");
        Learner learner3 = swimmingSchool.getLearnerByLearnerId("L3");
        Learner learner4 = swimmingSchool.getLearnerByLearnerId("L4");
        Learner learner5 = swimmingSchool.getLearnerByLearnerId("L5");

        Booking b1 = new Booking("B1L1", LocalDate.now(), learner1, swimmingLesson1, BookingStatus.BOOKED.name(), null);
        Booking b2 = new Booking("B2L2", LocalDate.now(), learner2, swimmingLesson2, BookingStatus.BOOKED.name(), null);
        Booking b3 = new Booking("B3L3", LocalDate.now(), learner3, swimmingLesson3, BookingStatus.BOOKED.name(), null);
        Booking b4 = new Booking("B4L4", LocalDate.now(), learner4, swimmingLesson4, BookingStatus.BOOKED.name(), null);
        Booking b5 = new Booking("B5L5", LocalDate.now(), learner5, swimmingLesson5, BookingStatus.BOOKED.name(), null);

        learner1.getBookings().add(b1);
        swimmingSchool.getBookings().put(b1.getBookingID(), b1);
        swimmingLesson1.setAvailableSlots(swimmingLesson1.getAvailableSlots() - 1);

        learner2.getBookings().add(b2);
        swimmingSchool.getBookings().put(b2.getBookingID(), b2);
        swimmingLesson2.setAvailableSlots(swimmingLesson2.getAvailableSlots() - 1);

        learner3.getBookings().add(b3);
        swimmingSchool.getBookings().put(b3.getBookingID(), b3);
        swimmingLesson3.setAvailableSlots(swimmingLesson3.getAvailableSlots() - 1);

        learner4.getBookings().add(b4);
        swimmingSchool.getBookings().put(b4.getBookingID(), b4);
        swimmingLesson4.setAvailableSlots(swimmingLesson4.getAvailableSlots() - 1);

        learner5.getBookings().add(b5);
        swimmingSchool.getBookings().put(b5.getBookingID(), b5);
        swimmingLesson5.setAvailableSlots(swimmingLesson5.getAvailableSlots() - 1);

    }

    /**
     * Generates reviews for attended bookings.
     *
     * @param swimmingSchool The swimming school instance.
     */
    private static void generateReviews(SwimmingSchool swimmingSchool) {

        Review review1 = new Review(Rating.SATISFIED, "L1", 2, LocalDate.of(2024, 3, 18), LocalDate.of(2024, 3, 20), "Great lesson!");
        Review review2 = new Review(Rating.VERY_SATISFIED, "L2", 3, LocalDate.of(2024, 3, 18), LocalDate.of(2024, 3, 20), "Fantastic coaching!");
        Review review3 = new Review(Rating.OK, "L3", 4, LocalDate.of(2024, 3, 18), LocalDate.of(2024, 3, 20), "Enjoyed the session!");


        Booking booking1 = swimmingSchool.getBookings().get("B1L1");
        booking1.setBookingStatus(BookingStatus.ATTENDED.name());
        SwimmingLesson swimmingLesson1 = booking1.getLesson();
        swimmingLesson1.setAvailableSlots(swimmingLesson1.getAvailableSlots() + 1);
        swimmingLesson1.getReviews().add(review1);
        booking1.setReview(review1);

        Booking booking2 = swimmingSchool.getBookings().get("B2L2");
        booking2.setBookingStatus(BookingStatus.ATTENDED.name());
        SwimmingLesson swimmingLesson2 = booking2.getLesson();
        swimmingLesson2.setAvailableSlots(swimmingLesson2.getAvailableSlots() + 1);
        swimmingLesson2.getReviews().add(review2);
        booking2.setReview(review2);

        Booking booking3 = swimmingSchool.getBookings().get("B3L3");
        booking3.setBookingStatus(BookingStatus.ATTENDED.name());
        SwimmingLesson swimmingLesson3 = booking3.getLesson();
        swimmingLesson3.setAvailableSlots(swimmingLesson3.getAvailableSlots() + 1);
        swimmingLesson3.getReviews().add(review3);
        booking3.setReview(review3);

    }

}
