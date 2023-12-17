package cd.project.backend.services;

import cd.project.backend.domain.Lounge;
import org.junit.jupiter.api.Test;

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
        assertNotEquals(
                -1,
                BookingServiceHelpers.createBooking(
                'A',
                LocalDate.of(2024, 6, 18),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                5,
                1
        ));

        assertNotEquals(
                -1,
                BookingServiceHelpers.createBooking(
                'B',
                LocalDate.of(2024, 6, 19),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                8,
                1
        ));

        assertNotEquals(
                -1,
                BookingServiceHelpers.createBooking(
                'A',
                LocalDate.of(2024, 6, 18),
                LocalTime.of(14, 0),
                LocalTime.of(18,0),
                14,
                1
        ));
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
}