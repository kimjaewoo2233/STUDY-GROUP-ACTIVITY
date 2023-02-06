package org.hotel.back.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id",insertable = false,updatable = false)
    private Hotel hotel;    //호텔


    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<RoomImage> roomImage = new HashSet<>();

    private String roomNumber;  //방번호

    private String roomClass;   //방등급

    private String roomPrice;   //방가격

    private String roomLimit;   //제한인원

    private String description; //방설명


    @Column(name = "hotel_id")
    private long hotelId;


    public void addImage(String fileName){
            this.roomImage.add(RoomImage.builder()
                            .name(fileName)
                            .room(this)
                    .build());
    }
}
