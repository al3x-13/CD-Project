package cd.project.backend.services;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceHelpersTest {

    @Test
    void getBookingIdsForDateAndTime_ValidEntries() {
        ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList(1, 2, 3, 4, 5, 6, 7) );
        ArrayList<Integer> realResult = BookingServiceHelpers.getBookingIdsForDateAndTime(
                LocalDate.of(2024, 6, 12),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );
        assertEquals(expectedResult, realResult);

        expectedResult = new ArrayList<>( Arrays.asList(8, 9, 10, 11) );
        realResult = BookingServiceHelpers.getBookingIdsForDateAndTime(
                LocalDate.of(2024, 6, 13),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );
        assertEquals(expectedResult, realResult);

        expectedResult = new ArrayList<>( Arrays.asList(12, 13, 14) );
        realResult = BookingServiceHelpers.getBookingIdsForDateAndTime(
                LocalDate.of(2024, 6, 14),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0)
        );
        assertEquals(expectedResult, realResult);
    }

    @Test
    void getBookingIdsForDateAndTime_NoBookings() {
        ArrayList<Integer> realResult = BookingServiceHelpers.getBookingIdsForDateAndTime(
                LocalDate.of(2024, 6, 9),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0)
        );
        assertNull(realResult);
    }

    @Test
    void getAvailableLounges() {
        ArrayList<String> expectedReesult = new ArrayList<>(Arrays.asList(
                "",
                ""
        ));
    }

    @Test
    void getBookingIdsForDate() {
    }

    @Test
    void testGetBookingIdsForDateAndTime() {
    }

    @Test
    void getRemainingLoungeSeats() {
    }

    @Test
    void getTotalLoungeSeats() {
    }

    @Test
    void createBooking() {
    }
}