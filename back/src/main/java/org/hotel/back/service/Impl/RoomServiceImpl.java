package org.hotel.back.service.Impl;


import lombok.RequiredArgsConstructor;
import org.hotel.back.config.exception.FileUploadException;
import org.hotel.back.config.exception.FileViewException;
import org.hotel.back.data.dto.FileDTO;
import org.hotel.back.data.dto.UploadDTO;
import org.hotel.back.data.response.FileResponseData;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.domain.Room;
import org.hotel.back.domain.RoomImage;
import org.hotel.back.repository.RoomRepository;
import org.hotel.back.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    @Value("${upload.path}")
    private String path;

    private final RoomRepository roomRepository;


    public List<FileDTO> upload(UploadDTO uploadDTO){
        if(!uploadDTO.getHotelImage().isEmpty()){

            final List<FileDTO> list = new ArrayList<>();

            uploadDTO.getHotelImage().forEach(multipartFile -> {
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(path, uuid+"_"+multipartFile.getOriginalFilename());

                try{
                    multipartFile.transferTo(savePath);
                } catch (IOException e) {
                    throw new FileUploadException();
                }

                list.add(FileDTO.builder()
                                .uuid(uuid)
                                .fileName(multipartFile.getOriginalFilename())
                        .build());
            });
            return list;
        }
        return null;
       }

       public FileResponseData viewFile(String fileName){
            Resource resource = new FileSystemResource(path+ File.separator+fileName);

            String resourceName = resource.getFilename();
            HttpHeaders headers = new HttpHeaders();

            try{
                headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
            } catch (IOException e){
                throw new FileViewException();
            }
            return FileResponseData.builder().headers(headers).resource(resource).build();

       }

        public Map<String,Boolean> removeFile(String fileName){
                Resource resource = new FileSystemResource(path + File.separator + fileName);
                String resourceName = resource.getFilename();

                Map<String, Boolean> resultMap = new HashMap<>();
                boolean removed = false;


                try{
                    String contentType = Files.probeContentType(resource.getFile().toPath());
                    removed = resource.getFile().delete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                resultMap.put("result", removed);
                return resultMap;
        }




       public void save(RoomDTO roomDTO){
                roomRepository.save(toEntity(roomDTO));
       }
       public RoomDTO findByRoomWithImage(Long id){
                return toDTO(roomRepository.getRoomWithImage(id));
       }




       protected Room toEntity(RoomDTO dto){
                Room room = Room.builder()
                        .description(dto.getDescription())
                        .roomPrice(dto.getRoomPrice())
                        .roomLimit(dto.getRoomLimit())
                        .roomNumber(dto.getRoomNumber())
                        .roomClass(dto.getRoomClass())
                        .build();

                if(!dto.getFileNames().isEmpty()){
                    dto.getFileNames().forEach(room::addImage);
                   }
                return room;
     }
     protected RoomDTO toDTO(Room room) {
         RoomDTO dto = RoomDTO.builder()
                 .description(room.getDescription())
                 .roomPrice(room.getRoomPrice())
                 .roomLimit(room.getRoomLimit())
                 .roomNumber(room.getRoomNumber())
                 .roomClass(room.getRoomClass())
                 .hotelId(room.getHotelId())
                 .build();

         dto.setFileNames(room.getRoomImage()
                 .stream()
                 .sorted()
                 .map(RoomImage::getName)
                 .collect(Collectors.toList()));
         return dto;
     }





}
