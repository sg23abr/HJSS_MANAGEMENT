package org.hjss;

import org.hjss.constants.BookingStatus;
import org.hjss.constants.Grade;
import org.hjss.models.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main class for the HJSS Management Tool.
 */
public class Main {
    private static final SwimmingSchool swimmingSchool = new SwimmingSchool();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method to start the HJSS Management Tool.
     */
    public static void main(String[] args) {
        while (true) {
            printMenu();
            int input = scanner.nextInt();
            processInput(input);
        }
    }

    /**
     * Prints the main menu of the application.
     */
    private static void printMenu() {
        System.out.println("_________________________________________________");
        System.out.println("************* Welcome to HJSS *****************");
        System.out.println("*************************************************");
        System.out.println("Select an option:");
        System.out.println("1. View Whole Timetable");
        System.out.println("2. View Timetable by Day");
        System.out.println("3. View Timetable by Grade Level");
        System.out.println("4. View Timetable by Coach's Name");
        System.out.println("5. Book a Lesson");
        System.out.println("6. Change Booking");
        System.out.println("7. Cancel Booking");
        System.out.println("8. Mark Your Booking as Attended");
        System.out.println("9. Generate 4 Weeks of Booking Report");
        System.out.println("10. Generate Average Rating Report for Coaches");
        System.out.println("11. View all Available Bookings");
        System.out.println("12. Add a new Learner");
        System.out.println("13. View all Learners");
        System.out.println("Select your option or 0 to exit:");
    }


    /**
     * Processes the user input and performs corresponding actions.
     *
     * @param input The user input
     */
    private static void processInput(int input) {
        switch (input) {
            case 0:
                System.exit(0);
            case 1:
                viewWholeTimetable();
                break;
            case 2:
                viewTimetableByDay();
                break;
            case 3:
                viewTimetableByGrade();
                break;
            case 4:
                viewTimetableByCoach();
                break;
            case 5:
                bookLesson();
                break;
            case 6:
                changeBooking();
                break;
            case 7:
                cancelBooking();
                break;
            case 8:
                markBookingAttended();
                break;
            case 9:
                generateReport();
                break;
            case 10:
                swimmingSchool.generateCoachRatingsReport();
                break;
            case 11:
                viewAvailableBookings();
                break;
            case 12:
                addNewLearner();
                break;
            case 13:
                viewAvailableLearners();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }


    /**
     * Displays the whole timetable. option 1
     */
    private static void viewWholeTimetable() {
        viewTimetable(swimmingSchool.viewTimetable(null, 0, null));
    }

    /**
     * Displays the timetable for a specific day. option 2
     */
    private static void viewTimetableByDay() {
        System.out.println("Enter day (Monday, Wednesday, Friday, Saturday):");
        String dayInput = scanner.next();
        DayOfWeek day = DayOfWeek.valueOf(dayInput.toUpperCase());
        viewTimetable(swimmingSchool.viewTimetable(day, 0, null));
    }

    /**
     * Displays the timetable for a specific grade level. option 3
     */
    private static void viewTimetableByGrade() {
        System.out.println("Enter grade level (1, 2, 3, 4, 5):");
        int grade = scanner.nextInt();
        viewTimetable(swimmingSchool.viewTimetable(null, grade, null));
    }

    /**
     * Displays the timetable for a specific coach. option 4
     */
    private static void viewTimetableByCoach() {
        System.out.println("Enter coach's name:");
        String coachName = scanner.next();
        viewTimetable(swimmingSchool.viewTimetable(null, 0, coachName));
    }

    /**
     * Books a swimming lesson. option 5
     */
    private static void bookLesson() {
        viewAvailableLearners();
        viewTimetableOption();

        System.out.println("Now Enter learner ID to proceed with the booking: ");
        String learnerId = scanner.next();

        LocalDate date = getDateInput();
        LocalTime time = getTimeInput(date);

        System.out.println(swimmingSchool.bookLesson(time, date, learnerId));
    }

    /**
     * Changes the booking details. option 6
     */
    private static void changeBooking() {

        viewAvailableBookedBookings();

        String bookingId = null;
        boolean validBookingId = false;
        Booking booking = null;
        while (!validBookingId) {
            System.out.print("Enter booking ID to change: ");
            bookingId = scanner.next();
            booking = swimmingSchool.getBookingById(bookingId);

            if (booking == null) {
                System.out.println("Invalid Booking ID! Please try again.");
            } else {
                validBookingId = true;
            }
        }
        System.out.println("You are changing booking details of: " + booking.getLearner().getName() + " for booking ID: " + booking.getBookingID());
        viewTimetableOption();
        // Get day of the week from the user
        LocalDate date = getDateInput();

        // Get time slot from the user
        LocalTime time = getTimeInput(date);

        System.out.println(swimmingSchool.changeBooking(bookingId.trim(), time, date));
    }

    /**
     * Cancels a booking. option 7
     */
    private static void cancelBooking() {
        viewAvailableBookedBookings();
        System.out.print("Enter booking ID to cancel: ");
        String bookingIdToCancel = scanner.next();
        System.out.println(swimmingSchool.cancelBooking(bookingIdToCancel));
    }

    /**
     * Marks a booking as attended. option 8
     */
    private static void markBookingAttended() {
        viewAvailableBookedBookings();
        try {
            System.out.print("Enter booking Id: ");
            String bookingId = scanner.next();
            if (bookingId == null || bookingId.isEmpty()) {
                System.out.println("Enter a valid booking id.");
                return;
            }
            String reply = swimmingSchool.markBookingAttended(bookingId.trim());
            System.out.println(reply);

            // Prompt the user to give a rating
            System.out.println("Now please provide your rating for this booking:");
            System.out.println("Enter 1 for VERY DISSATISFIED");
            System.out.println("Enter 2 for DISSATISFIED");
            System.out.println("Enter 3 for OK");
            System.out.println("Enter 4 for SATISFIED");
            System.out.println("Enter 5 for VERY SATISFIED");
            System.out.print("Enter rating (1 to 5): ");
            int rating = scanner.nextInt();
            System.out.println(swimmingSchool.provideReview(bookingId, rating));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generates a booking report. option 9
     */
    public static void generateReport() {
        System.out.println("Select the type of report:");
        System.out.println("1. Summary of bookings");
        System.out.println("2. Detailed information for each booking");
        int reportType = scanner.nextInt();
        switch (reportType) {
            case 1:
                // Generate summary of bookings
                System.out.println("Enter month number (1-12) for the report:");
                int monthNumber = scanner.nextInt();
                swimmingSchool.generateMonthlySummaryOfBookings(monthNumber);
                break;
            case 2:
                // Generate detailed information for each booking
                System.out.println("Enter month number (1-12) for the report:");
                monthNumber = scanner.nextInt();
                swimmingSchool.generateDetailedLearnerReport(monthNumber);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    /**
     * Displays all available bookings. option 11
     */
    private static void viewAvailableBookings() {

        List<Booking> availableBookings = new ArrayList<>(swimmingSchool.getBookings().values());

        System.out.println("Available Bookings:");
        System.out.printf("%-10s | %-15s | %-12s | %-12s | %-15s | %-12s | %-12s | %-10s%n",
                "Booking ID", "Learner Name", "Current Grade", "Booked Grade", "Lesson Date", "Lesson Day", "Lesson Time", "Booking Status");

        for (Booking availableBooking : availableBookings) {
            Learner learner = availableBooking.getLearner();
            SwimmingLesson lesson = availableBooking.getLesson();
            Grade learnerCurrentGrade = learner.getCurrentGrade();
            Grade learnerBookedGrade = lesson.getGrade();
            String status = availableBooking.getBookingStatus();
            LocalDate lessonDate = lesson.getDate();
            DayOfWeek lessonDay = lesson.getDate().getDayOfWeek();
            String lessonTime = formatTimeSlot(lesson.getTimeSlot());


            System.out.printf("%-10s | %-15s | %-12s | %-12s | %-15s | %-12s | %-12s | %-10s%n",
                    availableBooking.getBookingID(), learner.getName(), learnerCurrentGrade,
                    learnerBookedGrade, lessonDate, lessonDay, lessonTime, status);
        }
    }

    /**
     * Adds a new learner. option 12
     */
    private static void addNewLearner() {
        System.out.println("Enter learner name:");
        String name = scanner.next();

        System.out.println("Enter learner gender (male/female):");
        String gender = scanner.next();

        int age;
        while (true) {
            System.out.println("Enter learner age between (4 to 11):");
            age = scanner.nextInt();
            if (age >= 4 && age <= 11) {
                break;
            } else {
                System.out.println("Invalid age! Please enter an age between 4 and 11.");
            }
        }

        System.out.println("Enter emergency contact:");
        String emergencyContact = scanner.next();

        int grade;
        while (true) {
            System.out.println("Enter learner grade (1-5):");
            grade = scanner.nextInt();
            if (grade >= 1 && grade <= 5) {
                break;
            } else {
                System.out.println("Invalid grade! Please enter a grade between 1 and 5.");
            }
        }
        swimmingSchool.addLearner(name, gender, age, emergencyContact, grade);
    }

    /**
     * Displays all learners. option 13
     */
    public static void viewAvailableLearners() {
        System.out.println("Check out the Available Learners in the system:");
        System.out.println("-------------------------------");
        System.out.printf("%-5s | %-15s | %-10s%n", "ID", "Name", "Grade");
        System.out.println("-------------------------------");
        for (Learner learner : swimmingSchool.getLearners()) {
            System.out.printf("%-5s | %-15s | %-10s%n", learner.getId(), learner.getName(), learner.getCurrentGrade());
        }
    }


    /**
     * This is used as formatted view for timetable
     * @param swimmingLessons
     */
    public static void viewTimetable(List<SwimmingLesson> swimmingLessons) {
        System.out.println("_______________________________________________________________________________________");
        System.out.printf("| %-5s | %-10s | %-12s | %-10s | %-15s | %-10s |%n",
                "Grade", "Day", "Date", "Time", "Coach", "Available Slots");
        System.out.println("_______________________________________________________________________________________");

        for (SwimmingLesson swimmingLesson : swimmingLessons) {
            Coach coach = swimmingLesson.getCoach();
            System.out.printf("| %-5s | %-10s | %-12s | %-10s | %-15s | %-10s |%n",
                    swimmingLesson.getGrade(),
                    swimmingLesson.getDate().getDayOfWeek(),
                    swimmingLesson.getDate(),
                    swimmingLesson.getTimeSlot(),
                    coach.getName(),
                    swimmingLesson.getAvailableSlots());
        }
    }



    /**
     * This is a formatted view for Bookings whose status is BOOKED
     * @return
     */
    private static List<Booking> viewAvailableBookedBookings() {
        List<Booking> availableBookings = new ArrayList<>();
        for (Booking booking : swimmingSchool.getBookings().values()) {
            if (booking.getBookingStatus().equals(BookingStatus.BOOKED.name())) {
                availableBookings.add(booking);
            }
        }
        System.out.println("Available Bookings:");
        System.out.printf("%-10s | %-15s | %-12s | %-12s | %-15s | %-12s | %-12s | %-10s%n",
                "Booking ID", "Learner Name", "Current Grade", "Booked Grade", "Lesson Date", "Lesson Day", "Lesson Time", "Booking Status");

        for (Booking availableBooking : availableBookings) {
            Learner learner = availableBooking.getLearner();
            SwimmingLesson lesson = availableBooking.getLesson();
            Grade learnerCurrentGrade = learner.getCurrentGrade();
            Grade learnerBookedGrade = lesson.getGrade();
            String status = availableBooking.getBookingStatus();
            LocalDate lessonDate = lesson.getDate();
            DayOfWeek lessonDay = lesson.getDate().getDayOfWeek();
            String lessonTime = formatTimeSlot(lesson.getTimeSlot());


            System.out.printf("%-10s | %-15s | %-12s | %-12s | %-15s | %-12s | %-12s | %-10s%n",
                    availableBooking.getBookingID(), learner.getName(), learnerCurrentGrade,
                    learnerBookedGrade, lessonDate, lessonDay, lessonTime, status);
        }
        return availableBookings;
    }

    /**
     * This is a input method for viewing timetable
     */
    private static void viewTimetableOption() {
        System.out.println("In case you want to view the timetable, select an option from below or enter 0 to proceed with the booking:");
        System.out.println("1. View timetable by day");
        System.out.println("2. View timetable by grade level");
        System.out.println("3. View timetable by coach's name");

        int viewOption = scanner.nextInt();
        switch (viewOption) {
            case 0:
                break;
            case 1:
                viewTimetableByDay();
                break;
            case 2:
                viewTimetableByGrade();
                break;
            case 3:
                viewTimetableByCoach();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    /**
     * A Util method which takes the time input from user by displaying the avsilable time slots
     * @param date
     * @return
     */
    private static LocalTime getTimeInput(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        LocalTime[] lessonTimes = {LocalTime.of(16, 0), LocalTime.of(17, 0), LocalTime.of(18, 0)};
        LocalTime[] saturdayLessonTimes = {LocalTime.of(14, 0), LocalTime.of(15, 0)};
        LocalTime[] availableTimes = (dayOfWeek == DayOfWeek.SATURDAY) ? saturdayLessonTimes : lessonTimes;

        // Display available time slots for the given day
        System.out.println("Available time slots:");
        for (LocalTime lessonTime : availableTimes) {
            System.out.println(formatTimeSlot(lessonTime));
        }

        // Get time slot from the user
        LocalTime time = null;
        boolean validInput = false;
        do {
            System.out.print("Enter time (HH:mm): ");
            String timeInput = scanner.next();
            try {
                time = LocalTime.parse(timeInput);
                for (LocalTime availableTime : availableTimes) {
                    if (time.equals(availableTime)) {
                        validInput = true;
                        break;
                    }
                }
                if (!validInput) {
                    throw new IllegalArgumentException("Invalid time slot. Please enter a valid time slot.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!validInput);
        return time;
    }

    /**
     * This is util for formatting LocalTime to String AM and PM for user readability
     * @param time
     * @return
     */
    private static String formatTimeSlot(LocalTime time) {
        // Format time slot in desired format
        return String.format("%02d:%02d (%d:%02d %s)",
                time.getHour(),
                time.getMinute(),
                time.getHour() > 12 ? time.getHour() - 12 : time.getHour(),
                time.getMinute(),
                time.getHour() < 12 ? "AM" : "PM");
    }

    /**
     * This is a method to get the date input from the user
     * @return
     */
    private static LocalDate getDateInput() {
        LocalDate date = null;
        boolean validInput = false;
        do {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String dateInput = scanner.next();
            try {
                date = LocalDate.parse(dateInput);
                validInput = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        } while (!validInput);
        return date;
    }
}