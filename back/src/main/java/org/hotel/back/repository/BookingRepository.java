package org.hotel.back.repository;

import org.hotel.back.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    //fetch lazy 해줘야됨.
}
