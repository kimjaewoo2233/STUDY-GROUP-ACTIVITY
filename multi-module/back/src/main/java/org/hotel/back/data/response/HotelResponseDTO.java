package org.hotel.back.data.response;

import lombok.*;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.HotelImage;
import org.hotel.back.domain.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Setter
@AllArgsConstructor
@ToString
@Getter
public class HotelResponseDTO {

    private Long id;
    private String hotelName;
    private String cityName;
    private String tellNumber;
    private String latitude;
    private String longitude;

    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    private String address;
    private Double ratingAvg;

    @Builder.Default
    private List<String>hotelImages=new ArrayList<>();

    public HotelResponseDTO(Hotel hotel,Double avg){
        this.id=hotel.getId();
        this.hotelName=hotel.getHotelName();
        this.cityName=hotel.getCityName();
        this.tellNumber=hotel.getTellNumber();
        this.latitude=hotel.getLatitude();
        this.longitude=hotel.getLongitude();
        //this.hotelImages=hotel.getHotelImages().stream().map(entity-> entity.getName()).collect(Collectors.toList());
        this.reviews=hotel.getReviews();
        this.ratingAvg=avg;
    }



}
