package org.hotel.back.data.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UploadDTO {

    private List<MultipartFile> hotelImage;

}
