package org.hotel.back.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@EntityListeners(AuditingEntityListener.class)
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "hotel")
    private List<HotelImage> hotelImages = new ArrayList<>();
    @CreatedBy
    String writer;
    String address;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Review> reviews = new ArrayList<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "hotel")
    private List<Booking> bookingList = new ArrayList<>();


    @ToString.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "hotel")
    private Set<Room> roomSet = new HashSet<>();

    public void modifyHotel(String hotelName, String cityName, String tellNumber, String latitude, String longitude){
        this.hotelName=hotelName;
        this.cityName=cityName;
        this.tellNumber=tellNumber;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public void addRoom(Room room){

        this.roomSet.add(room);
    }


}
