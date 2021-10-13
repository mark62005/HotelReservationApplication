package model;

import java.util.Locale;
import java.util.Objects;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double roomPrice;
    private final RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) &&
                Objects.equals(roomPrice, room.roomPrice) &&
                roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomPrice, roomType);
    }

    @Override
    public String toString() {
        return "Room{ " +
                "Room number: '" + roomNumber + '\'' +
                ", Room price per night: $" + roomPrice +
                ", Room type: '" + String.valueOf(roomType).toLowerCase(Locale.ROOT) + " room'" +
                " }";
    }

}
