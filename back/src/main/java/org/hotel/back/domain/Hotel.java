package org.hotel.back.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String hotelName;
    String cityName;
    String tellNumber;
    String latitude;
    String longitude;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Review> reviews = new ArrayList<>();
    
    public void modifyHotel(String hotelName, String cityName, String tellNumber, String latitude, String longitude){
        this.hotelName=hotelName;
        this.cityName=cityName;
        this.tellNumber=tellNumber;
        this.latitude=latitude;
        this.longitude=longitude;
    }

}
