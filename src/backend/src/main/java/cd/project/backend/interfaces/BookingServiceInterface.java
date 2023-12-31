package cd.project.backend.interfaces;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BookingServiceInterface extends Remote {
    String test(String data) throws RemoteException;

    int getPort() throws RemoteException;

    /**
     * Gets all the available lounges for the provided beach in the specified date
     * and time interval.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @return Available lounges
     */
   ArrayList<Lounge> listAvailableLounges(
           char beachId,
           LocalDate date,
           LocalTime fromTime,
           LocalTime toTime
   ) throws RemoteException;

    /**
     * Check booking availability for the provided details.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @param individuals amount of people
     * @return Available lounges or null if no availability
     */
   ArrayList<Lounge> checkBookingAvailability(
           char beachId,
           LocalDate date,
           LocalTime fromTime,
           LocalTime toTime,
           int individuals
   ) throws RemoteException;

    /**
     * Creates a new lounge booking with the provided details.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @param individuals amount of people
     * @return Booking ID or -1 on failure
     */
   int createBooking(
           char beachId,
           LocalDate date,
           LocalTime fromTime,
           LocalTime toTime,
           int individuals,
           int userId
   ) throws RemoteException;

    /**
     * Cancels a booking.
     * @param bookingId booking id
     * @return Whether the booking was successfully canceled
     */
   boolean cancelBooking(int bookingId) throws RemoteException;

    /**
     * Gets all the booking for the specified user.
     * @param userId user id
     * @return Bookings List
     */
   ArrayList<Booking> getUserBookings(int userId) throws RemoteException;

    /**
     * Check if the user owns the booking.
     * @param userId user id
     * @param bookingId booking id
     * @return Whether the user owns the booking
     */
   boolean userOwnsBooking(int userId, int bookingId) throws RemoteException;
}
