package org.hotel.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.data.request.BookingRequestDTO;
import org.hotel.back.data.request.RegisterData;
import org.hotel.back.data.response.BookingResponseDTO;
import org.hotel.back.domain.Gender;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Member;
import org.hotel.back.repository.BookingRepository;
import org.hotel.back.service.BookingService;
import org.hotel.back.service.HotelService;
import org.hotel.back.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@Slf4j
public class BookingContoller {

    private final BookingService bookingService;
    private final HotelService hotelService;
    private final MemberService memberService;


    /*view 예약된 정보 조회*/
    @GetMapping("/booking")
    public String bookingRead(Model model, @RequestParam Long id){
        model.addAttribute("booking",bookingService.read(id));
        return "booking";
    }



    /*view - 예약하기 버튼으로 수행되는 저장
    * requestDTO의 setter를 통해 바인딩 된 form데이터 저장
    * prg패턴으로 예약완료된 페이지 보여줌. */
    @GetMapping("/booking/save")
    public void bookingSave(@RequestParam Long id){
        //호텔 객체에 id값으로 조회된 호텔 불러오기
        Hotel hotel = hotelService.hotelDetail(id);
        //멤버 객체 임시 생성.resisterData 생성.
        RegisterData registerData = RegisterData.builder()
                .email("test@naver.com")
                .password("abcde")
                .tellNumber("010-1234-5678")
                .gender("MAN")
                .nickName("test")
                .build();

        memberService.registerSave(registerData); //boolean값임..
        Member member = memberService.findMember(registerData.getEmail());//member 찾아오기

                //request 객체 생성하여 전달.
        BookingRequestDTO bookingRequestDTO = BookingRequestDTO.builder()
                .hotel(hotel)
                .member(member)
                .checkIn(LocalDateTime.of(2000,01,01,01,01,01))
                .check_out(LocalDateTime.of(2000,01,01,01,01,01))
                .build();

        //null에러 방지를 위해 localdateTime 기본값 입력.

        bookingService.save(bookingRequestDTO);
       /* return "redirect:/booing";*/
    }


    /*수정
    * 조회된 예약정보에서 입실 기능으로 수행되는 수정기능
    * 입실 및 퇴실시간 자동등록*/
    @PostMapping("/booking/modify")
    public String bookingModify(@RequestBody BookingRequestDTO bookingRequestDTO){
        bookingService.modify(bookingRequestDTO);
        return "redirect:/booking";
    }



    /*삭제
    * 예약취소버튼으로 수행되는 삭제
    * prg패턴으로 삭제이후 메인페이지 보여줌.*/
    @GetMapping("/booking/delete")
    public String bookingDelete(@RequestParam Long id){
        bookingService.delete(id);
        return "redirect:/hotel";
    }

}
