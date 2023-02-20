package org.hotel.back.data.response;


import lombok.*;
import org.hotel.back.domain.Hotel;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;


    private String roomNumber;  //방번호

    private String roomClass;   //방등급

    private String roomPrice;   //방가격

    private String roomLimit;   //제한인원
    private String description; //방설명


    private String hotelId;
    private List<String> fileNames;// 파일

}
