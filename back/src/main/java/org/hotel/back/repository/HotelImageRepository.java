package org.hotel.back.repository;

import org.hotel.back.domain.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelImageRepository extends JpaRepository<HotelImage, String> {
}
