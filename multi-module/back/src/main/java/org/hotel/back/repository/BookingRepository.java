package org.hotel.back.repository;

import org.hotel.back.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Transactional
    @Modifying
    @Query("update Booking b set b.checkIn = :checkIn, b.checkOut = :checkOut where b.id = :id")
    void updateBooking(@Param("id") Long id, @Param("checkIn") LocalDateTime checkIn, @Param("checkOut")LocalDateTime checkOut);
}
