package main.wjbos.zenhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import main.wjbos.zenhotel.Entity.Room;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //allows me to return only the data I want
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private UserDTO user;
    private RoomDTO room;
    private BookingDTO booking;

    private List<UserDTO> userList;
    private List<RoomDTO> roomList;
    private List<BookingDTO> bookingList;


}
