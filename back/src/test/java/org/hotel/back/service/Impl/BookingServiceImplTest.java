package org.hotel.back.service.Impl;

import org.hotel.back.data.request.BookingRequestDTO;
import org.hotel.back.domain.Gender;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Member;
import org.hotel.back.repository.BookingRepository;
import org.hotel.back.service.BookingService;
import org.hotel.back.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceImplTest {

    @Autowired
    BookingService bookingService;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    HotelService hotelService;

    @Test
    @Transactional
    @DisplayName("save 테스트")
    void testSave() {
 /*       Hotel hotel = hotelService.hotelDetail(90L);

        Member member = Member.builder()
                .email("test1")
                .password("test1")
                .tellNumber("011-0202-0202")
                .gender(Gender.WOMAN)
                .nickName("mic")
                .build();

        BookingRequestDTO bookingRequestDTO = BookingRequestDTO.builder()
                .hotel(hotel)
                .member(member)
                .build();



        System.out.println("====================");
        System.out.println("저장 테스트");
        bookingService.save(bookingRequestDTO);
        System.out.println(bookingRequestDTO.getBookingId());
*/

    }

    @Test
    @Transactional
    @DisplayName("read 테스트")
    void testRead(){

        /*        System.out.println(bookingService.findAll());
        System.out.println("====================");
        System.out.println("조회 테스트");
        System.out.println(bookingService.read(2L));*/

    }

    @Test
    @Transactional
    @DisplayName("read 테스트")
    void testmodify(){


/*
        System.out.println("====================");
        System.out.println("수정 테스트");
        System.out.println(bookingService.modify(brt));
        System.out.println(bookingRequestDTO.getCheckIn() + " \n" +
                bookingRequestDTO.getCheck_out());}
 */

    }
/*    @Test
    @Transactional
    @DisplayName("read 테스트")
    void testDelete(){

            BookingRequestDTO brt = BookingRequestDTO.builder()
                .bookingId(20L)
                .hotel(hotel)
                .member(member)
                .build();

        System.out.println("====================");
        System.out.println("삭제 테스트");
        System.out.println(bookingService.delete(1L));
    }*/


}