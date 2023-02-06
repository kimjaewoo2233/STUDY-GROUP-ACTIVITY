package org.hotel.back.data.response;

import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseData {

    private HttpHeaders headers;

    private Resource resource;

}
