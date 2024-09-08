package main.wjbos.zenhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the UserDTO class to map the User Entity.
 * @Data uses Lombok to generate getters and setters
 * @JsonInclude(JsonInclude.Include.NON_NULL) is used to exclude null values from the response
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;
    private List<BookingDTO> bookings = new ArrayList<>();

}
