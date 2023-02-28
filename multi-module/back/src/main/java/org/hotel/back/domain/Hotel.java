package org.hotel.back.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;
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
    private Long id;
    private String hotelName;
    private String cityName;
    private String tellNumber;
    private String latitude;
    private String longitude;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "hotel")
    @BatchSize(size=20)
    private List<HotelImage> hotelImages = new ArrayList<>();
    @CreatedBy
    private String writer;
    private String address;

    @BatchSize(size = 10)
    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel",fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "hotel")
    private List<Booking> bookingList = new ArrayList<>();

    @BatchSize(size = 10)
    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "hotel",fetch = FetchType.LAZY)
    private List<HotelImage> hotelImages = new ArrayList<>();

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
