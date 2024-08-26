package main.wjbos.zenhotel.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull(message = "Check in date is required")
    private LocalDate checkInDate;
    @Future(message = "Check out date Needs to be a future date")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of Adults cannot be less than 1")
    private int numOfAdults;

    @Min(value = 0, message = "Number of Children cannot be negative")
    private int numOfChildren;

    private int totalGuests;

    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id",referencedColumnName = "id")
    private Room room;

    public void calculateTotalNumOfGuests(){
        this.totalGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(@Min(value = 1, message = "Number of Adults cannot be less than 1") int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalNumOfGuests();
    }

    public void setNumOfChildren(@Min(value = 0, message = "Number of Children cannot be negative") int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumOfGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalGuests=" + totalGuests +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
