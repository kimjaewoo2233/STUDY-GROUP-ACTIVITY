package org.hotel.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.config.exception.BookingErrorCode;
import org.hotel.back.config.exception.BookingErrorResponse;
import org.hotel.back.config.exception.BookingException;
import org.hotel.back.data.dto.BookingDTO;
import org.hotel.back.data.dto.MemberDTO;
import org.hotel.back.data.request.BookingRequestDTO;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.domain.*;
import org.hotel.back.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingContoller {
    private final BookingService bookingService;

    /**
     * 예약 가능한 방 불러오기
     * */
    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> availableRooms(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut){
        LocalDateTime localDateTimeIn = checkIn.atTime(12,0);
        LocalDateTime localDateTimeOut = checkOut.atTime(12,0);
        List<RoomDTO> availableRoomList = bookingService.findAvailable(localDateTimeIn, localDateTimeOut);

        return ResponseEntity.ok(availableRoomList);
    }

    /**
     *
     * */

    @PostMapping("/save")
    public Boolean bookingSave(@RequestBody @Valid BookingRequestDTO bookingRequestDTO,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal MemberDTO memberDTO){
        if(memberDTO == null){
            throw new BookingException(BookingErrorCode.BOOKING_SAVE_FAIL);
        }
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error("ERROR Message {} ", objectError.getDefaultMessage());
            });
        }else{
            BookingDTO dto = BookingDTO.bookingDTO(bookingRequestDTO, localDateTimeParser(bookingRequestDTO.getCheckIn()), localDateTimeParser(bookingRequestDTO.getCheckOut()), memberDTO);
            bookingService.bookingSave(dto);
        }

        return true;
    }


    @GetMapping("/list") //예약 리스트 전부 불러오기
    public List<Booking> bookingList(){
        return bookingService.getBooking();
    }
    @GetMapping("/find/{id}") //id로 예약정보 한개 찾기
    public BookingDTO findBooking(@RequestParam("id") Long id){
        BookingDTO dto = bookingService.findById(id);
        return dto;
    }

    @PutMapping("/modify/{id}") // 수정
    public Boolean updateBooking(@RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal Principal principal) {
        System.out.println(bookingDTO.getCheckIn().getClass().getName());
        if (principal.getName().equals(bookingDTO.getMemberEmail())) {
            bookingService.updateBooking(bookingDTO.getCheckIn(), bookingDTO.getCheckOut(), bookingDTO.getMemberEmail(), bookingDTO.toEntity().getId());
            return true;
        } else if (principal.getName().equals(null)) {
            throw new NullPointerException();
        }else return false;
    }

    @DeleteMapping("/delete/{id}") // 삭제
    public Boolean deleteBooking(@PathVariable("id") Long id){
        bookingService.delete(id);
        return true;
    }

    public LocalDateTime localDateTimeParser(String date){
        String dateString = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(dateString, formatter);
        LocalDateTime localDateTimeOut = dateTime.atTime(12, 0);

        return localDateTimeOut;
    }

//    @ExceptionHandler(Exception.class)
//    public BookingErrorResponse handleException(BookingException e){
//        return BookingErrorResponse.builder()
//                .errorCode(e.getBookingErrorCode())
//                .errorMessage(e.getDetailMessage())
//                .build();
//    }
}
