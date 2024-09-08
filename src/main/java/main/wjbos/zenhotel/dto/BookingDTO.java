package main.wjbos.zenhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

/**
 * This is the DTO for the Booking entity.
 * contains all the fields of the Booking entity.
 *
 * @Data marks the fields as getters and setters
 * @JsonInclude(JsonInclude.Include.NON_NULL) removes fields that are null
 */


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    public Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numOfAdults;
    private int numOfChildren;
    private int totalGuests;
    private String bookingConfirmationCode;
    private UserDTO user;
    private RoomDTO room;

}
