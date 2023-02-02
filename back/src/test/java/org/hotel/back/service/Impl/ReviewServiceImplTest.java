package org.hotel.back.service.Impl;

import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Review;
import org.hotel.back.dto.request.ReviewRequestDTO;
import org.hotel.back.repository.HotelRepository;
import org.hotel.back.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewServiceImplTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    HotelRepository hotelRepository;
    @Test
    @DisplayName("리뷰insert")
    void saveReview() {
    }


}