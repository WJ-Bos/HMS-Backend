package main.wjbos.zenhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import main.wjbos.zenhotel.Entity.Booking;

import java.math.BigDecimal;
import java.util.List;

/**
 * RoomDTO Used to map Room Entity
 * @Data uses Lombok for getters and setters
 * @JsonInclude is used to remove null values from JSON
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;

    private List<BookingDTO> bookings ;
}
