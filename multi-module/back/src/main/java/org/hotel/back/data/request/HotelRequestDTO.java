package org.hotel.back.data.request;

import lombok.*;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@AllArgsConstructor
@ToString
@Getter
@NoArgsConstructor
public class HotelRequestDTO {
    private Long id;
    private String hotelName;
    private String cityName;
    private String tellNumber;
    private String latitude;
    private String longitude;
    private String address;
    private List<MultipartFile> hotelImage;

    public static Hotel toEntity(HotelRequestDTO hotelRequestDTO){
        return Hotel.builder()
                .id(hotelRequestDTO.getId())
                .hotelName(hotelRequestDTO.getHotelName())
                .cityName(hotelRequestDTO.getCityName())
                .tellNumber(hotelRequestDTO.getTellNumber())
                .latitude(hotelRequestDTO.getLatitude())
                .longitude(hotelRequestDTO.getLongitude())
                .address(hotelRequestDTO.getAddress())
                .build();
    }

}
