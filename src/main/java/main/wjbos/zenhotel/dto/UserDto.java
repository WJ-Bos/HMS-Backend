package main.wjbos.zenhotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import main.wjbos.zenhotel.Entity.Booking;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;
    private List<Booking> bookings = new ArrayList<>();


}
