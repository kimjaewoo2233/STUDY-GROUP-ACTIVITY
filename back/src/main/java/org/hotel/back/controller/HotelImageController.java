package org.hotel.back.controller;

import lombok.RequiredArgsConstructor;
import org.hotel.back.data.dto.FileDTO;
import org.hotel.back.service.HotelService;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.hotel.back.data.dto.UploadDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequiredArgsConstructor
public class HotelImageController {
    @Value("${upload.path}")
    private String uploadPath;
    private final HotelService hotelService;
    @PostMapping(value = "/hotel/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileDTO>> saveFile(UploadDTO dto){
        var response = hotelService.upload(dto);
        System.out.println(response.get(0).getLink());

        if(ObjectUtils.isEmpty(response)){
            return ResponseEntity.ok(new ArrayList<FileDTO>());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hotel/image/view")    //여기에 요청 넣는 거에요
    public ResponseEntity<Resource> viewFile(@RequestParam String fileName){
        var response = hotelService.viewFile(fileName);
        if(ObjectUtils.isEmpty(response)){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getResource());

    }
    @DeleteMapping("/hotel/image/delete")
    public Map<String,Boolean> deleteFile(@RequestParam String fileName){

        return hotelService.removeFile(fileName);
    }
}
