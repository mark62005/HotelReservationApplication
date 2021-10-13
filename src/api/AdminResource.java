package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Map;
import java.util.Set;

public class AdminResource {

    private static AdminResource instance;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    // provide static reference
    private AdminResource(){}

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

    public Map<String, IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Map<String, Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Set<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public void cancelReservation(long id) {
        reservationService.cancelReservation(id);
    }

    public void removeCustomer(String email) {
        customerService.removeCustomer(email);
    }

    public void removeRoom(String roomNumber) {
        reservationService.removeRoom(roomNumber);
    }

    public void clearCustomerSamples() {
        customerService.clearCustomerSamples();
    }

    public void clearRoomSamples() {
        reservationService.ClearRoomSamples();
    }

    public void clearReservationSamples() {
        reservationService.clearReservationSamples();
    }

}
