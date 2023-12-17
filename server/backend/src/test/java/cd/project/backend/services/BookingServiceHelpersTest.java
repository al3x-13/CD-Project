package cd.project.backend.services;

import cd.project.backend.database.DbConnection;
import cd.project.backend.domain.Lounge;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

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
        assertNotNull(realResult);
        Collections.sort(realResult);
        assertEquals(expectedResult, realResult);

        expectedResult = new ArrayList<>( Arrays.asList(8, 9, 10, 11) );
        realResult = BookingServiceHelpers.getBookingIdsForDateAndTime(
                LocalDate.of(2024, 6, 13),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );
        assertNotNull(realResult);
        Collections.sort(realResult);
        assertEquals(expectedResult, realResult);

        expectedResult = new ArrayList<>( Arrays.asList(12, 13, 14) );
        realResult = BookingServiceHelpers.getBookingIdsForDateAndTime(
                LocalDate.of(2024, 6, 14),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0)
        );
        assertNotNull(realResult);
        Collections.sort(realResult);
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
    void getAvailableLounges_LoungesLeft() {
        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(
                "A6","A7","A8","A9","A10","A14","A15","A19","A20"
        ));
        ArrayList<String> realResult = new ArrayList<>(
                BookingServiceHelpers.getAvailableLounges(
                    'A',
                    LocalDate.of(2024, 6, 12),
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0)
                ).stream().map(Lounge::getId).collect(Collectors.toList())
        );
        boolean containsAllValues = true;
        for (String id : realResult) {
            if (!expectedResult.contains(id)) {
                containsAllValues = false;
                break;
            }
        }
        assertTrue(containsAllValues);

        expectedResult = new ArrayList<>(Arrays.asList(
                "B4","B5","B9","B10"
        ));
        realResult = new ArrayList<>(
                BookingServiceHelpers.getAvailableLounges(
                        'B',
                        LocalDate.of(2024, 6, 13),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                ).stream().map(Lounge::getId).collect(Collectors.toList())
        );
        for (String id : realResult) {
            if (!expectedResult.contains(id)) {
                containsAllValues = false;
                break;
            }
        }
        assertTrue(containsAllValues);
    }

    @Test
    void getAvailableLounges_NoLoungesLeft() {
        assertNull(BookingServiceHelpers.getAvailableLounges(
                'C',
                LocalDate.of(2024, 6, 14),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0)
        ));
    }

    @Test
    void createBooking_ValidBookings() {
        int booking1 = BookingServiceHelpers.createBooking(
                'A',
                LocalDate.of(2024, 6, 18),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                5,
                1
        );
        assertNotEquals(-1, booking1);

        int booking2 = BookingServiceHelpers.createBooking(
                'B',
                LocalDate.of(2024, 6, 19),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                8,
                1
        );
        assertNotEquals(-1, booking2);

        int booking3 = BookingServiceHelpers.createBooking(
            'A',
                LocalDate.of(2024, 6, 18),
                LocalTime.of(14, 0),
                LocalTime.of(18,0),
                14,
                1
        );
        assertNotEquals(-1, booking3);

        // cleanup
        DbConnection.executeUpdate(
                "DELETE FROM bookings WHERE id IN (?, ?, ?)",
                booking1,
                booking2,
                booking3
        );
    }

    @Test
    void createBooking_InvalidBookings() {
        assertEquals(
                -1,
                BookingServiceHelpers.createBooking(
                'A',
                LocalDate.of(2024, 6, 12),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                30,
                1
        ));

        assertEquals(
                -1,
                BookingServiceHelpers.createBooking(
                'C',
                LocalDate.of(2024, 6, 14),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                5,
                1
        ));

        assertEquals(
                -1,
                BookingServiceHelpers.createBooking(
                'C',
                LocalDate.of(2024, 6, 14),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                1,
                1
        ));
    }

    @Test
    void cancelBooking_ValidBookings() {
        ArrayList<String> bookingLounges = new ArrayList<>(Arrays.asList(
                "A8", "A16"
        ));
        DbConnection.executeUpdate(
                "INSERT INTO bookings (id, beach_id, date, from_time, to_time, user_id, lounge_ids) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                323,
                'A',
                LocalDate.of(2024, 7, 18),
                LocalTime.of(13, 0),
                LocalTime.of(17, 0),
                1,
                DbConnection.stringListToVarcharArray(bookingLounges)
        );

        bookingLounges = new ArrayList<>(Arrays.asList( "B3", "B10", "B11" ));
        DbConnection.executeUpdate(
                "INSERT INTO bookings (id, beach_id, date, from_time, to_time, user_id, lounge_ids) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                324,
                'B',
                LocalDate.of(2024, 7, 19),
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                1,
                DbConnection.stringListToVarcharArray(bookingLounges)

        );

        bookingLounges = new ArrayList<>(Arrays.asList( "C3", "C4", "C5", "C9" ));
        DbConnection.executeUpdate(
                "INSERT INTO bookings (id, beach_id, date, from_time, to_time, user_id, lounge_ids) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                325,
                'C',
                LocalDate.of(2024, 7, 20),
                LocalTime.of(9, 0),
                LocalTime.of(16, 0),
                1,
                DbConnection.stringListToVarcharArray(bookingLounges)

        );

        assertTrue(BookingServiceHelpers.cancelBooking(323));
        assertTrue(BookingServiceHelpers.cancelBooking(324));
        assertTrue(BookingServiceHelpers.cancelBooking(325));
    }

    @Test
    void cancelBooking_InvalidBookings() {
        assertFalse(BookingServiceHelpers.cancelBooking(878));
        assertFalse(BookingServiceHelpers.cancelBooking(5235));
        assertFalse(BookingServiceHelpers.cancelBooking(2349));
    }
}