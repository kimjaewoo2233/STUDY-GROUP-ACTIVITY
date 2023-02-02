package org.hotel.back.data.response;

import lombok.*;
import org.hotel.back.domain.Booking;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Member;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class BookingResponseDTO {
    private Long bookingId;
    private Hotel hotel;
    private Member member;
    private LocalDateTime checkIn;
    private LocalDateTime check_out;

    public Booking toEntity(BookingResponseDTO bookingResponseDTO){
        return Booking.builder()
                .bookingId(bookingResponseDTO.getBookingId())
                .hotel(bookingResponseDTO.getHotel())
                .member(bookingResponseDTO.getMember())
                .checkIn(bookingResponseDTO.getCheckIn())
                .check_out(bookingResponseDTO.getCheck_out())
                .build();
    }

}
