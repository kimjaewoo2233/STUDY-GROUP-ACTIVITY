package org.hotel.back.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Booking extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    @Column(nullable = false)
    private LocalDateTime checkIn;
    @Column(nullable = false)
    private LocalDateTime check_out;


}
