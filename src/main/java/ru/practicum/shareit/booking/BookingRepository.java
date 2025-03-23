package ru.practicum.shareit.booking;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Booking AS b " +
            "SET b.status = :status " +
            "WHERE b.id = :id")
    void bookingApprove(@Param("id") Long bookingId, @Param("status") BookingStatus newStatus);

    List<Booking> findAllByUserId(long userId);

    List<Booking> findAllByUserIdAndEndingBefore(long userId, LocalDateTime now);

    List<Booking> findAllByUserIdAndEndingAfter(long userId, LocalDateTime now);

    @Query("SELECT b " +
            "FROM Booking AS b  " +
            "WHERE b.bookerId = :id " +
            "AND :now BETWEEN b.start AND b.ending" )
    List<Booking> findAllByUserIdCurrentBooking(@Param("id") long userId, @Param("now") LocalDateTime now);
}
