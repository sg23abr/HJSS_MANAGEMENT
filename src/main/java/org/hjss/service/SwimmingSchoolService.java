package org.hjss.service;

import org.hjss.constants.BookingStatus;
import org.hjss.constants.Grade;
import org.hjss.constants.Rating;
import org.hjss.exception.*;
import org.hjss.models.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class providing operations related to booking, marking attendance,
 * cancelling bookings, providing reviews, and generating reports for a swimming school.
 */
public class SwimmingSchoolService {

    /**
     * Method to book a swimming lesson for a learner.
     *
     * @param swimmingLessonToBook The swimming lesson to book.
     * @param learner              The learner who wants to book the lesson.
     * @param bookingMap           The map of bookings to update.
     * @return The ID of the booking.
     * @throws InvalidLesson       If the lesson is invalid.
     * @throws InvalidBooking     If the booking is invalid.
     * @throws NoSlotsAvailable   If no slots are available for the lesson.
     * @throws AlreadyRegistered  If the learner is already registered for the lesson.
     */
    public String bookLesson(SwimmingLesson swimmingLessonToBook, Learner learner, Map<String, Booking> bookingMap)
            throws InvalidLesson, InvalidBooking, NoSlotsAvailable, AlreadyRegistered {
            if (swimmingLessonToBook != null && learner != null) {
                String bookingId = learner.getId() + getEpochTime(LocalDate.now());
            if (swimmingLessonToBook.getAvailableSlots()<=0) {
                throw new NoSlotsAvailable("No slots available for lesson " + swimmingLessonToBook.getGrade() + " on " + swimmingLessonToBook.getDate());
            }
            // Check if the learner's current grade matches the grade of the lesson or one grade higher
            if (learner.getCurrentGrade().getValue() <= swimmingLessonToBook.getGrade().getValue()
                    && swimmingLessonToBook.getGrade().getValue() <= learner.getCurrentGrade().getValue() + 1 ||
                    learner.getCurrentGrade().getValue() >= swimmingLessonToBook.getGrade().getValue()) {

                // check for already registered case
                List<Booking> leanerBookings = learner.getBookings();
                if(leanerBookings != null) {
                    for (Booking booking : leanerBookings) {
                        if (booking.getLesson().getGrade().equals(swimmingLessonToBook.getGrade())
                                && booking.getLesson().getDate().isEqual(swimmingLessonToBook.getDate())) {
                            throw new AlreadyRegistered(
                                    "You have already registered for the lesson with Id: " + booking.getBookingID());
                        }
                    }
                }

                // Create a new booking
                Booking booking = new Booking();
                booking.setBookingID(bookingId); // Generate a unique booking ID
                booking.setBookingDate(swimmingLessonToBook.getDate());
                booking.setLearner(learner);
                booking.setLesson(swimmingLessonToBook);
                booking.setBookingStatus(BookingStatus.BOOKED.toString());

                // Add the booking to the learner's bookings
                learner.getBookings().add(booking);

                // Update the learner's current grade if they attended a higher grade lesson
                if (swimmingLessonToBook.getGrade().getValue() == learner.getCurrentGrade().getValue() + 1) {
                    learner.setCurrentGrade(swimmingLessonToBook.getGrade());
                }

                // Update the booking in the SwimmingSchool's bookings map
                bookingMap.put(bookingId, booking);

                swimmingLessonToBook.setAvailableSlots(swimmingLessonToBook.getAvailableSlots()-1);

                System.out.println("****** You have successfully registered the lesson Grade: " + swimmingLessonToBook.getGrade() + " on " + swimmingLessonToBook.getDate() + " with booking Id : " + bookingId + " ********");
                return bookingId;
            } else {
                throw new InvalidBooking("Learner cannot book this lesson. It's either too advanced or not available for their grade.");
            }
        } else {
            throw new InvalidLesson("Lesson or learner not found with given details retry again with valid details.");
        }
    }

    /**
     * Method to mark a booking as attended.
     *
     * @param booking The booking to mark as attended.
     * @return A message indicating the result of marking the booking as attended.
     * @throws InvalidBooking If the booking is invalid.
     */
    public String markBookingAttended(Booking booking) throws InvalidBooking {
        if (booking == null) {
            throw new InvalidBooking("Invalid booking details.");
        }

        if (booking.getBookingStatus().equals(BookingStatus.CANCELLED.name())) {
            throw new InvalidBooking("Lesson is cancelled and cannot be changed");
        }

        if (booking.getLesson().getDate().isAfter(LocalDate.now())) {
            throw new InvalidBooking("Cannot mark attended as the lesson has not yet started yet");
        }

        booking.setBookingStatus(BookingStatus.ATTENDED.name());
        booking.getLesson().setAvailableSlots(booking.getLesson().getAvailableSlots() + 1);

        return "Learner - " + booking.getLearner().getId() + " has attended the Lesson " + booking.getLesson().getGrade() + " on " + booking.getLesson().getDate();
    }

    /**
     * Method to cancel a booking.
     *
     * @param bookedLesson The booking to cancel.
     * @return A message indicating the result of cancelling the booking.
     * @throws InvalidBooking If the booking is invalid.
     * @throws InvalidDate    If the date is invalid.
     */
    public String cancelBooking(Booking bookedLesson) throws InvalidBooking, InvalidDate {

        if (bookedLesson == null) {
            throw new InvalidBooking("Invalid booking details.");
        }

        if (bookedLesson.getBookingStatus().equals(BookingStatus.ATTENDED.name())) {
            throw new InvalidBooking("Invalid booking details. Booking is already attended or changed");
        }
        if (bookedLesson.getBookingDate().isBefore(LocalDate.now())) {
            throw new InvalidDate("Lesson already attended. Cancel Rejected.");
        }

        bookedLesson.setBookingStatus(BookingStatus.CANCELLED.name());
        bookedLesson.getLesson().setAvailableSlots(bookedLesson.getLesson().getAvailableSlots() + 1);

        return "Your booking : " + bookedLesson.getBookingID() + " for lesson " + bookedLesson.getLesson().getGrade() + " on " + bookedLesson.getLesson().getDate() + " has been cancelled successfully.";
    }

    /**
     * Method to provide a review for a booking.
     *
     * @param learner The learner providing the review.
     * @param booking The booking to review.
     * @param rating  The rating provided in the review.
     * @return A message indicating the result of providing the review.
     * @throws InvalidLearner If the learner is invalid.
     * @throws InvalidBooking If the booking is invalid.
     * @throws InvalidDate    If the date is invalid.
     * @throws InvalidRating  If the rating is invalid.
     */
    public String provideReview(Learner learner, Booking booking, int rating) throws InvalidLearner, InvalidBooking, InvalidDate, InvalidRating {

        if (learner == null) {
            throw new InvalidLearner("Learner does not exist.");
        }

        if (booking == null) {
            throw new InvalidBooking("Please enter correct booking details.");
        }

        String learnerId = learner.getId();
        String bookingId = booking.getBookingID();

        if (!booking.getLearner().getId().equals(learnerId)) {
            throw new InvalidLearner("Learner " + learnerId + " is invalid for booking " + bookingId);
        }

        if (!booking.getBookingStatus().equals(BookingStatus.ATTENDED.name())) {
            throw new InvalidDate("Lesson " + booking.getLesson().getGrade() + " has not been attended by customer " + learnerId);
        }

        if (rating < 1 || rating > 5) {
            throw new InvalidRating("Rating can only be between 1 and 5.");
        }

        LocalDate today = LocalDate.now();
        Rating valueOfRating = Rating.valueOfRating(rating);

        Review review = new Review(valueOfRating, learnerId, booking.getLesson().getGrade().getValue(), booking.getLesson().getDate(), today, null);

        if (booking.getLesson().getReviews() == null) {
            booking.getLesson().setReviews(new ArrayList<>());
        }

        booking.getLesson().getReviews().add(review);
        booking.setReview(review);

        return "Learner " + learnerId + " has rated " + rating + " for lesson " + booking.getLesson().getGrade();
    }

    /**
     * Method to update/change a booking.
     *
     * @param booking             The booking to update/change.
     * @param learner             The learner associated with the booking.
     * @param swimmingLessonToBook The new swimming lesson to book.
     * @return A message indicating the result of updating/changing the booking.
     * @throws InvalidLesson       If the lesson is invalid.
     * @throws InvalidBooking     If the booking is invalid.
     * @throws NoSlotsAvailable   If no slots are available for the lesson.
     * @throws AlreadyRegistered  If the learner is already registered for the lesson.
     * @throws InvalidDate         If the date is invalid.
     */
    public String changeBooking(Booking booking, Learner learner, SwimmingLesson swimmingLessonToBook) throws InvalidLesson, InvalidBooking, NoSlotsAvailable, AlreadyRegistered, InvalidDate {

        if (booking == null) {
            throw new InvalidBooking("Invalid booking");
        }

        if (booking.getLesson().getDate().isBefore(LocalDate.now())) {
            throw new InvalidDate("Learner - " + learner.getId() + " has already attended the session " + booking.getLesson().getGrade() + " on " + booking.getLesson().getDate() + ". Change not allowed");
        }
        // Check if the learner's current grade matches the grade of the lesson or one grade higher
        if (learner.getCurrentGrade().getValue() <= swimmingLessonToBook.getGrade().getValue()
                && swimmingLessonToBook.getGrade().getValue() <= learner.getCurrentGrade().getValue() + 1
        || learner.getCurrentGrade().getValue() >= swimmingLessonToBook.getGrade().getValue()) {

            if (swimmingLessonToBook.getCapacity() <= 0) {
                throw new NoSlotsAvailable("No slots available for lesson " + swimmingLessonToBook.getGrade() + " on " + swimmingLessonToBook.getDate());
            }

            // Check if the learner is already registered for the new lesson
            for (Booking existingBooking : learner.getBookings()) {
                if (existingBooking.getLesson().getGrade().equals(swimmingLessonToBook.getGrade())
                        && existingBooking.getLesson().getDate().isEqual(swimmingLessonToBook.getDate())) {
                    throw new AlreadyRegistered("You have already registered for the lesson with Id: " + existingBooking.getBookingID());
                }
            }

            // Update the previous lesson's capacity
            booking.getLesson().setAvailableSlots(booking.getLesson().getAvailableSlots() + 1);

            // Update the existing booking with the new lesson details
            booking.setLesson(swimmingLessonToBook);

            // Update the learner's current grade if they attended a higher grade lesson
            if (swimmingLessonToBook.getGrade().getValue() == learner.getCurrentGrade().getValue() + 1) {
                learner.setCurrentGrade(swimmingLessonToBook.getGrade());
            }

            // Update the new lesson's capacity
            swimmingLessonToBook.setAvailableSlots(swimmingLessonToBook.getAvailableSlots() - 1);

            return "Your Booking " + booking.getBookingID() + " has been successfully changed to lesson " + swimmingLessonToBook.getGrade() + " on " + swimmingLessonToBook.getDate();

        } else {
            throw new InvalidBooking("Learner cannot book this lesson. It's either too advanced or not available for their grade.");
        }
    }

    /**
     * Method to generate a detailed monthly report of learner information.
     *
     * @param startOfMonth The start date of the month.
     * @param endOfMonth   The end date of the month.
     */
    public void generateDetailedLearnerReport(LocalDate startOfMonth, LocalDate endOfMonth, List<Learner> learners) {

        for (Learner learner : learners) {
            for (Booking booking : learner.getBookings()) {
                SwimmingLesson swimmingLesson = booking.getLesson();
                Review review = booking.getReview();

                // Check if the lesson falls within the specified month
                LocalDate lessonDate = swimmingLesson.getDate();
                if (lessonDate.isEqual(startOfMonth) || (lessonDate.isAfter(startOfMonth) && lessonDate.isBefore(endOfMonth))) {
                    String rating = (review != null) ? review.getRating().name() : "-";
                    printBookingDetails(learner.getId(), booking.getBookingID(), swimmingLesson.getGrade(), swimmingLesson.getDate(),
                            swimmingLesson.getTimeSlot(), swimmingLesson.getCoach().getName(), booking.getBookingStatus(), rating);
                }
            }
        }
    }

    /**
     * Method to generate a coach average ratings report.
     */
    public void generateCoachRatingsReport(List<Coach> coaches, List<SwimmingLesson> timetable) {

        Map<String, List<Review>> coachReviewsMap = new HashMap<>(); // Map to store reviews for each coach

        // Initialize the map with empty lists for each coach
        for (Coach coach : coaches) {
            coachReviewsMap.put(coach.getName(), new ArrayList<>());
        }

        // Collect reviews for each coach
        for (SwimmingLesson swimmingLesson : timetable) {
            List<Review> reviews = swimmingLesson.getReviews();
            for (Review review : reviews) {
                List<Review> coachReviews = coachReviewsMap.get(swimmingLesson.getCoach().getName());
                coachReviews.add(review);
            }
        }

        // Calculate and display average ratings for each coach
        for (Map.Entry<String, List<Review>> entry : coachReviewsMap.entrySet()) {
            String coachName = entry.getKey();
            List<Review> reviews = entry.getValue();

            double totalRating = 0;
            int numRatings = 0;

            // Calculate total rating and count number of ratings
            for (Review review : reviews) {
                totalRating += review.getRating().getValue();
                numRatings++;
            }

            // Calculate average rating
            double avgRating = numRatings > 0 ? totalRating / numRatings : 0;

            // Display coach name and average rating
            System.out.println(coachName + "\t\t" + avgRating);
        }

    }

    /**
     * Method to generate a monthly summary of bookings.
     *
     * @param startOfMonth The start date of the month.
     * @param endOfMonth   The end date of the month.
     */
    public void generateMonthlySummaryOfBookings(LocalDate startOfMonth, LocalDate endOfMonth, List<Learner> learners) {

        for (Learner learner : learners) {
            String learnerName = learner.getName();
            String learnerId = learner.getId();
            String currentGrade = learner.getCurrentGrade().name();
            int booked = 0, cancelled = 0, attended = 0, changed = 0;

            for (Booking booking : learner.getBookings()) {
                SwimmingLesson swimmingLesson = booking.getLesson();
                LocalDate lessonDate = swimmingLesson.getDate();

                // Check if the lesson falls within the specified month
                if ((lessonDate.isEqual(startOfMonth) || lessonDate.isAfter(startOfMonth)) && lessonDate.isBefore(endOfMonth)) {
                    switch (booking.getBookingStatus()) {
                        case "BOOKED":
                            booked++;
                            break;
                        case "CANCELLED":
                            cancelled++;
                            break;
                        case "ATTENDED":
                            attended++;
                            break;
                        case "CHANGED":
                            changed++;
                            break;
                    }
                }
            }

            System.out.printf("%-10s | %-24s | %-13s | %-6s | %-8s | %-9s | %-8s%n",
                    learnerId, learnerName, currentGrade, booked, changed, cancelled, attended);
        }
    }

    // Helper method to print booking details...
    private void printBookingDetails(String learnerId, String bookingId, Grade grade, LocalDate lessonDate,
                                     LocalTime time, String coach, String bookingStatus, String review) {
        System.out.printf("%-10s | %-9s | %-5s | %-13s | %-8s | %-9s | %-14s | %-6s%n",
                learnerId, bookingId, grade, lessonDate, time, coach, bookingStatus, review);
    }

    // Helper method to get epoch time...
    private Long getEpochTime(LocalDate date) {
        return System.currentTimeMillis();
    }
}
