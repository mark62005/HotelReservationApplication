package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final List<Reservation> reservations = new ArrayList<>();

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

        reservations.add(reservation);
        // return reservation to display it for customer
        return reservation;

    }

    public List<Reservation> getReservationsOfACustomer(String email) {

        // find reservation which matches the same email, and return a new list
        return reservations.stream()
                .filter((r) -> r.getCustomer().getEmail().equals(email.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());

    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

}
