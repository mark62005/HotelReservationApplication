package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReservationService {

    private static ReservationService instance;
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<Long, Reservation> reservations = new HashMap<>();

    // provide a static reference for Singleton Pattern
    private ReservationService(){}

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room) {

        String roomNumber = room.getRoomNumber();

        // make sure all te room numbers in room map are unique
        if (rooms.containsKey(roomNumber)) {
            throw new IllegalArgumentException("Error! This room number is already taken, please try another one.");
        }
        rooms.put(roomNumber, room);

    }

    public IRoom getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Map<String, IRoom> getAllRooms() {
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        reservations.put(reservation.getId(), reservation);
        // return reservation to display it for customer
        return reservation;

    }

    public List<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {

        // if the room list is empty, throw an Exception
        if (rooms.isEmpty()) {
            throw new NullPointerException("Sorry, there is no rooms created in our database");
        }

        return reservations.values().stream()
                // filter reservations which the check-in date input is after the check-out date of that reservation
                //  and the check-out date input is before the check-in date of that reservation
                .filter(r -> r.getCheckInDate().after(checkOutDate) || r.getCheckOutDate().before(checkInDate))
                .map(Reservation::getRoom)
                // sort the room list
                .sorted(Comparator.comparing(IRoom::getRoomNumber))
                .collect(Collectors.toList());

    }

    public Date addDays(Date date, int daysToAdd) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysToAdd);

        return calendar.getTime();

    }

    public List<IRoom> recommendAlternatives(Date checkInDate, Date checkOutDate) {

        // if all the rooms are booked for the original date range, add the date range for 7 days,
        //  and recommend a room list for that date range
        Date alternativeCheckInDate = addDays(checkInDate, 7);
        Date alternativeCheckOutDate = addDays(checkOutDate, 7);

        // base case: if the available room list is not empty, return that list
        if (!findAvailableRooms(alternativeCheckInDate, alternativeCheckOutDate).isEmpty()) {
            return findAvailableRooms(alternativeCheckInDate, alternativeCheckOutDate);
        }
        // recursive case
        return recommendAlternatives(alternativeCheckInDate, alternativeCheckOutDate);

    }

    public Map<Long, Reservation> getReservationsOfACustomer(String email) {

        // find reservation which matches the same email, and return a new list
        return reservations.values().stream()
                .filter((r) -> r.getCustomer().getEmail().equals(email.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toMap(Reservation::getId, Function.identity()));

    }

    public Map<Long, Reservation> getAllReservations() {
        return reservations;
    }

    public void cancelReservation(long id) {
        reservations.remove(id);
    }

    public void clearReservationSamples() {

        // find the list of reservation ids that contain "test."
        List<Long> ids = reservations.values().stream()
                .filter(r -> r.getCustomer().getEmail().contains("test."))
                .map(Reservation::getId)
                .collect(Collectors.toList());
        // remove them from the reservation list
        for (long id : ids) {
            cancelReservation(id);
        }

    }


    public void removeRoom(String roomNumber) {
        rooms.remove(roomNumber);
    }

    public void ClearRoomSamples() {

        // find the list of room numbers that contain "_test"
        List<String> roomNumbers = rooms.values().stream()
                .map(IRoom::getRoomNumber)
                .filter(number -> number.contains("_test"))
                .collect(Collectors.toList());
        // remove them from the room list
        for (String roomNumber : roomNumbers) {
            removeRoom(roomNumber);
        }

    }

}
