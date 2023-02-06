package org.hotel.back.data.request;

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
public class BookingRequestDTO {

    private Long bookingId;
    private Hotel hotel;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Member member;
    private LocalDateTime checkIn;
    private LocalDateTime check_out;


}
