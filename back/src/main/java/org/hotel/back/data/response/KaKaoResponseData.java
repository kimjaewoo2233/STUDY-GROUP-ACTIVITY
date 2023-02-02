package org.hotel.back.data.response;


import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
public class KaKaoResponseData {


    private String longitude;

    private String latitude;

}
