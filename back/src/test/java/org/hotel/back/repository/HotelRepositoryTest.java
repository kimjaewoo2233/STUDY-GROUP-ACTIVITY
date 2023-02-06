package org.hotel.back.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HotelRepositoryTest {
    @Autowired
    HotelRepository hotelRepository;
    @Test
    @Transactional
    void select(){
        System.out.println(hotelRepository.findJoinImages(116L));


    }


}