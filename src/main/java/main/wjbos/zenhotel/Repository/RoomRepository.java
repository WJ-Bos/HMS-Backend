package main.wjbos.zenhotel.Repository;

import main.wjbos.zenhotel.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();


    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id from Booking b)")
    List<Room> getAllAvailableRooms();


    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
    List<Room> findAvailableRoomByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
