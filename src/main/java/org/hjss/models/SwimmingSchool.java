package org.hjss.models;

import org.hjss.constants.Gender;
import org.hjss.constants.Grade;
import org.hjss.exception.InvalidBooking;
import org.hjss.service.SwimmingSchoolService;
import org.hjss.utils.SwimmingSchoolDataInitializer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * This class represents a swimming school with a timetable, coaches, learners, and booking management.
 */
public class SwimmingSchool {
    private final List<SwimmingLesson> timetable = new ArrayList<>();
    private final List<Coach> coaches = new ArrayList<>();
    private final List<Learner> learners = new ArrayList<>();
    private final Map<String, List<SwimmingLesson>> lessonMap = new HashMap<>();
    private final Map<String, Booking> bookings = new HashMap<>();
    private static SwimmingSchool swimmingSchool = null;
    SwimmingSchoolService swimmingSchoolService = new SwimmingSchoolService();

    /**
     * constructor which when initialises the default data
     */
    public SwimmingSchool() {
        SwimmingSchoolDataInitializer.initializeData(this);
    }


    /**
     * View the timetable based on provided parameters.
     *
     * @param dayOfWeek  The day of the week to filter by.
     * @param grade      The grade to filter by.
     * @param coachName  The coach's name to filter by.
     * @return A list of swimming lessons matching the provided criteria.
     */
    public List<SwimmingLesson> viewTimetable(DayOfWeek dayOfWeek, Integer grade, String coachName) {
        List<SwimmingLesson> filteredTimetable = new ArrayList<>();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Filter timetable based on the provided parameters and exclude past dates
        for (SwimmingLesson swimmingLesson : timetable) {
            LocalDate lessonDate = swimmingLesson.getDate();
            if (lessonDate.isAfter(today) && // Exclude past dates
                    (dayOfWeek == null || lessonDate.getDayOfWeek() == dayOfWeek) &&
                    (grade == 0 || Objects.equals(swimmingLesson.getGrade().getValue(), grade)) &&
                    (coachName == null || swimmingLesson.getCoach().getName().equals(coachName))) {
                filteredTimetable.add(swimmingLesson);
            }
        }

        if (dayOfWeek == null && grade == 0 && coachName == null) {
            for (SwimmingLesson swimmingLesson : timetable) {
                LocalDate lessonDate = swimmingLesson.getDate();
                if (lessonDate.isAfter(today)) {
                    filteredTimetable.add(swimmingLesson);
                }
            }
        }

        return filteredTimetable;
    }


    /**
     * Book a swimming lesson for a learner.
     *
     * @param time      The time of the lesson.
     * @param date      The date of the lesson.
     * @param learnerId The ID of the learner booking the lesson.
     * @return A message indicating the result of the booking attempt.
     */
    public String bookLesson(LocalTime time, LocalDate date, String learnerId) {
        System.out.println("----------- Booking Swimming Lesson -----------------");
        // Search for the lesson in the timetable based on the provided time and day
        SwimmingLesson swimmingLessonToBook = getLessonByTimeAndDay(time, date);
        try {
            // Find the learner with the given ID
            Learner learner = getLearnerByLearnerId(learnerId);
            return swimmingSchoolService.bookLesson(swimmingLessonToBook, learner, bookings);
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    /**
     * Change a booking to a different time and date.
     *
     * @param bookingId The ID of the booking to change.
     * @param time      The new time for the booking.
     * @param date      The new date for the booking.
     * @return A message indicating the result of the change attempt.
     */
    public String changeBooking(String bookingId, LocalTime time, LocalDate date) {
        System.out.println("----------- Changing Lesson Booking:-----------------");
        Booking booking = getBookingById(bookingId);
        Learner learner = booking.getLearner();
        SwimmingLesson swimmingLessonToBook = getLessonByTimeAndDay(time, date);
        try {
            return swimmingSchoolService.changeBooking(booking, learner, swimmingLessonToBook);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Get a swimming lesson by its time and date.
     *
     * @param time The time of the lesson.
     * @param date The date of the lesson.
     * @return The swimming lesson matching the provided time and date, or null if not found.
     */
    public SwimmingLesson getLessonByTimeAndDay(LocalTime time, LocalDate date) {
        for (SwimmingLesson swimmingLesson : timetable) {
            if (swimmingLesson.getTimeSlot().equals(time) && swimmingLesson.getDate().equals(date)) {
                return swimmingLesson;
            }
        }
        return null;
    }

    /**
     * Get a learner by their ID.
     *
     * @param learnerId The ID of the learner.
     * @return The learner object matching the provided ID, or null if not found.
     */
    public Learner getLearnerByLearnerId(String learnerId) {
        for (Learner l : learners) {
            if (l.getId().equals(learnerId)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Mark a booking as attended.
     *
     * @param bookingId The ID of the booking to mark as attended.
     * @return A message indicating the result of the operation.
     * @throws InvalidBooking If the booking is not valid.
     */
    public String markBookingAttended(String bookingId) throws InvalidBooking {
        Booking booking = getBookingById(bookingId);
            return swimmingSchoolService.markBookingAttended(booking);
    }

    /**
     * Get a booking by its ID.
     *
     * @param bookingId The ID of the booking.
     * @return The booking object matching the provided ID, or null if not found.
     */
    public Booking getBookingById(String bookingId) {
        return bookings.get(bookingId);
    }


    /**
     * Generate a report of coach ratings.
     */
    public void generateCoachRatingsReport() {
        System.out.println("------------ Coach Ratings Report --------------");
        System.out.println("Coach Name\tAverage Monthly Rating");

        swimmingSchoolService.generateCoachRatingsReport(getCoaches(), getTimetable());
    }


    /**
     * Cancel a booking.
     *
     * @param bookingId The ID of the booking to cancel.
     * @return A message indicating the result of the cancellation attempt.
     */
    public String cancelBooking(String bookingId) {
        Booking bookedLesson = getBookingById(bookingId);
        try {
            return swimmingSchoolService.cancelBooking(bookedLesson);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Provide a review for a booking.
     *
     * @param bookingId The ID of the booking to review.
     * @param rating    The rating to provide.
     * @return A message indicating the result of the review submission.
     */
    public String provideReview(String bookingId, int rating) {
        Booking booking = getBookingById(bookingId);
        Learner learner = booking.getLearner();
        try {
            return swimmingSchoolService.provideReview(learner, booking, rating);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    /**
     * Add a new learner to the swimming school.
     *
     * @param name            The name of the learner.
     * @param gender          The gender of the learner.
     * @param age             The age of the learner.
     * @param emergencyContact The emergency contact of the learner.
     * @param grade           The grade of the learner.
     * @return The ID of the newly added learner.
     */
    public String addLearner(String name, String gender, int age, String emergencyContact, int grade){
        Learner learner = new Learner();
        int sizeOfLearners = learners.size();
        String id = "L"+(sizeOfLearners+1);
        learner.setId(id);
        learner.setGender(Gender.valueOfGenderString(gender));
        learner.setName(name);
        learner.setAge(age);
        learner.setEmergencyContact(emergencyContact);
        learner.setCurrentGrade(Grade.valueOfGrade(grade));
        learner.setBookings(new ArrayList<>());
        learners.add(learner);
        System.out.println("New learner with ID " + id + " and name " + name + " has been added.");
        return id;
    }

    /**
     * Generate a detailed monthly report of learner information.
     *
     * @param monthNumber The number of the month to generate the report for.
     */
    public void generateDetailedLearnerReport(int monthNumber) {
        System.out.println("------------------------ Detailed Monthly Learner Information Report ------------------------------");
        System.out.println("LearnerID   | BookingID | Grade | Lesson Date   | Time      | Coach     | Booking Status | Review");
        System.out.println("___________________________________________________________________________________________________");

        LocalDate startOfMonth = LocalDate.now().withMonth(monthNumber).withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        swimmingSchoolService.generateDetailedLearnerReport(startOfMonth, endOfMonth, getLearners());
    }


    /**
     * Generate a monthly summary of bookings.
     *
     * @param monthNumber The number of the month to generate the summary for.
     */
    public void generateMonthlySummaryOfBookings(int monthNumber) {
        System.out.println("------------------------ Summary of Monthly Learners Bookings -------------------------------");
        System.out.println("LearnerID | Learner Name           | Current Grade | Booked | Changed | Cancelled | Attended");
        System.out.println("_____________________________________________________________________________________________");

        LocalDate startOfMonth = LocalDate.now().withMonth(monthNumber).withDayOfMonth(1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        swimmingSchoolService.generateMonthlySummaryOfBookings(startOfMonth, endOfMonth, getLearners());
    }


    /**
     * Get an instance of the SwimmingSchool (singleton pattern).
     *
     * @return The instance of the SwimmingSchool.
     */
    public static synchronized SwimmingSchool getInstance() {
        if (swimmingSchool == null) {
            swimmingSchool = new SwimmingSchool();
        }
        return swimmingSchool;
    }

    // Below all are Getters for instance variables...
    public List<SwimmingLesson> getTimetable() {
        return timetable;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public List<Learner> getLearners() {
        return learners;
    }

    public Map<String, Booking> getBookings() {
        return bookings;
    }
}
