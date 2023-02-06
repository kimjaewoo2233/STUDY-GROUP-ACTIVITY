package org.hotel.back.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.data.request.BookingRequestDTO;
import org.hotel.back.data.response.BookingResponseDTO;
import org.hotel.back.domain.Booking;
import org.hotel.back.repository.BookingRepository;
import org.hotel.back.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    /*
    * 관리자용
    * 예약내용 전체 가져오기.*/
    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    /*booking id를 통해 하나의 예약 정보를 가져온다.
    * (예약자 명으로 해야하나 고민중)
    * 레파지토리에서 아이디 값으로 찾아오고 null값일 경우 예외처리.
    * 반환 값은 DTO로 준다.*/
    @Override
    public BookingResponseDTO read(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return BookingResponseDTO.builder()

                .build();
    }

    /*저장기능
    * 요청받은 데이터를 엔티티로 바꿔 DB에 저장한다.
    * 예약정보가 생성된 시간과 체크인 시간은 현재시간으로 동일하게만듦.
    * 성공적으로 저장되면 false를 반환하고 콘솔에서 저장 내용을 확인할 수 있다.*/
    @Override
    public boolean save(BookingRequestDTO bookingRequestDTO) {
        bookingRequestDTO.setCreatedAt(LocalDateTime.now());
        //null에러 방지.
        bookingRequestDTO.setCheckIn(LocalDateTime.of(2000,01,01,01,01,01));
        bookingRequestDTO.setCheck_out(LocalDateTime.of(2000,01,01,01,01,01));
        bookingRequestDTO.setModifiedAt(LocalDateTime.of(2000,01,01,01,01,01));

    //    bookingRepository.save(BookingRequestDTO.toEntity(bookingRequestDTO));
        System.out.println(bookingRequestDTO); // 콘솔에서 확인용.
        return false;
    }
    /*수정기능
    * 엔티티의 modify기능으로 입실, 퇴실 시간 자동지정 및
    * 고객과 방 변경기능.*/
    @Override
    public boolean modify(BookingRequestDTO bookingRequestDTO) {
        Booking booking = bookingRepository.findById(bookingRequestDTO.getBookingId())
                .orElseThrow(RuntimeException::new);

        try {
            bookingRepository.save(booking);
        }catch (Exception e){
            return true;
        }
        return false;
    }

    /*삭제 기능
    * id 값으로 객체를 찾아서 삭제
    * 삭제에 성공하면 false를 반환*/
    @Override
    public boolean delete(Long id) {
        bookingRepository.deleteById(id);
        return false;
    }
}
