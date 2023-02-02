package org.hotel.back.dto.request;

import lombok.*;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Review;

@Builder
@Setter
@AllArgsConstructor
@ToString
@Getter
public class HotelRequestDTO {
    private Long id;
    private String hotelName;
    private String cityName;
    private String tellNumber;
    private String latitude;
    private String longitude;
    private Review review;
    String address;

    public static Hotel toEntity(HotelRequestDTO hotelRequestDTO){
        return Hotel.builder()
                .id(hotelRequestDTO.getId())
                .hotelName(hotelRequestDTO.getHotelName())
                .cityName(hotelRequestDTO.getCityName())
                .tellNumber(hotelRequestDTO.getTellNumber())
                .latitude(hotelRequestDTO.getLatitude())
                .longitude(hotelRequestDTO.getLongitude())
                .build();
    }

}
