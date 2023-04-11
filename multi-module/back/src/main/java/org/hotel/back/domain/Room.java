package org.hotel.back.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
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
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Booking> booking;

    @ToString.Exclude
    @Builder.Default
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<RoomImage> roomImage = new HashSet<>();

    private String roomNumber;  //방번호

    private String roomClass;   //방등급

    private String roomPrice;   //방가격

    private String roomLimit;   //제한인원

    private String description; //방설명


    private LocalDate checkIn;

    private LocalDate checkOut;


    @Column(name = "hotel_id")
    private long hotelId;


    public void addImage(String fileName){
        this.roomImage.add(RoomImage.builder()
                .name(fileName)
                .room(this)
                .build());
    }

    /**
     * @param roomPrice 첫번째
     * @param description 두번째
     * */
    public void changeRoomInfo(String roomPrice,String description){
        this.roomPrice = roomPrice;
        this.description = description;

    }

    //참조삭제
    public void imageInit(){
        this.roomImage.forEach(roomImage1 -> roomImage1.changeReference(null));

        this.roomImage.clear();
    }

}