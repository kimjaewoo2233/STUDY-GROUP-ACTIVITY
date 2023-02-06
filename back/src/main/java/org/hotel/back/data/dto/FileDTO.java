package org.hotel.back.data.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FileDTO {
    private String uuid;
    private String fileName;


    public String getLink(){
        return uuid+"_"+fileName;
    }
}
