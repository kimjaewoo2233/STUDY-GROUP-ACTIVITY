package org.hotel.back.data.request;

import lombok.*;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Review;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

    Long id;
    String reviewContent;
    String reviewWriter;
    Hotel hotel;
    public static Review toEntity(ReviewRequestDTO dto){
        return Review.builder()
                .reviewContent(dto.getReviewContent())
                .reviewWriter(dto.getReviewWriter())
                .hotel(dto.getHotel())
                .build();
    }
}
