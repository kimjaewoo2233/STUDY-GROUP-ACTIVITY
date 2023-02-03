package org.hotel.back.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotelImage extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    Long roomId;
    UUID uuid;
    String image_name;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    Hotel hotel;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

}
