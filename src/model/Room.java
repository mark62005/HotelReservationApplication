package model;

import java.util.Locale;

public class Room implements IRoom {

    private String roomNumber;
    private Double roomPrice;
    private RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room{ " +
                "Room number: '" + roomNumber + '\'' +
                ", Room price: $" + roomPrice +
                ", Room type: '" + String.valueOf(roomType).toLowerCase(Locale.ROOT) + '\'' +
                " }";
    }

}
