package com.back.bookingmodule.domain.booking;


import com.back.bookingmodule.domain.Member;
import com.back.bookingmodule.domain.Status;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Booking {      //TODO: 실제로 삭제가 아닌 값을 넘길 예정임

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email", insertable = false, updatable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime checkIn;
    @Column(nullable = false)
    private LocalDateTime checkOut;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "member_email")
    private String memberEmail;

    @ColumnDefault("false")
    private boolean deleted;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;


    public void changeBooking(LocalDateTime checkIn,LocalDateTime checkOut){
            this.checkIn = checkIn;
            this.checkOut = checkOut;
    }


    public void changeDeleting(){
            this.deleted = true;
    }

}
