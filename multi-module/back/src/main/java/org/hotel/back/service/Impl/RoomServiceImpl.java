package org.hotel.back.service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.config.exception.FileUploadException;
import org.hotel.back.config.exception.FileViewException;
import org.hotel.back.data.dto.FileDTO;
import org.hotel.back.data.dto.UploadDTO;
import org.hotel.back.data.response.FileResponseData;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.data.response.RoomResponseDTO;
import org.hotel.back.domain.Room;
import org.hotel.back.domain.RoomImage;
import org.hotel.back.repository.RoomRepository;
import org.hotel.back.service.RoomCacheService;
import org.hotel.back.service.RoomService;
import org.hotel.back.service.S3Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    @Value("${upload.path}")
    private String path;

    private final RoomRepository roomRepository;

    private final RoomCacheService roomCacheService;

    private final S3Config s3Config;



    public List<FileDTO> upload(UploadDTO uploadDTO){
        if(!uploadDTO.getFiles().isEmpty()){

            final List<FileDTO> list = new ArrayList<>();

            uploadDTO.getFiles().forEach(multipartFile -> {

                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(path, uuid+"_"+multipartFile.getOriginalFilename());

                try{
                    multipartFile.transferTo(savePath);
                } catch (IOException e) {
                    throw new FileUploadException("");
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

//        roomDTO.getFileNames().forEach(s -> {
//            String temp =s3Config.upload(s);
//            tempDTO.add(temp);
//        });


        roomDTO.getFileNames().forEach(s -> System.out.println("변경된 파일네임"+s));

        Room room = roomRepository.save(toEntity(roomDTO));

    }

    /**
     * @apiNote id값으로 redis를 먼저 조회한 후 데이터가 없다면 RDB를 뒤져서 가져온다
     *      RDB를 뒤져서 가져온 데이터는 바로 redis에 저장되고 다음 번 호출 때는 redis를 통해가져온다.
     *
     * */
    public RoomDTO findByRoomWithImage(Long id){

        RoomDTO roomDTO = roomCacheService.findById(id);

        if(roomDTO == null){
            log.info("Extract from data Repository and Insert Data redis");
            RoomDTO temp = toDTO(roomRepository.getRoomWithImage(id).orElseThrow(RuntimeException::new));
            roomCacheService.save(temp);
            return temp;
        }
        return roomDTO;
    }


    @Transactional
    public boolean modifyRoom(RoomDTO roomDTO){
        Room room = roomRepository.getRoomWithImage(roomDTO.getId()).orElse(null);


        RoomDTO cacheDTO = roomCacheService.findById(roomDTO.getId());
        if(cacheDTO != null) roomCacheService.delete(roomDTO.getId());

        if(room != null){
            room.changeRoomInfo(roomDTO.getRoomPrice(), roomDTO.getDescription());
            room.imageInit();

            if(roomDTO.getFileNames() != null){
                for(String fileName : roomDTO.getFileNames()){
                    room.addImage(fileName);
                }
            }

            roomRepository.save(room);
            // 이미지까지 저장하는 것이기에 dirty Checking 불가
            return true;
        }else{
            return false;
        }
    }

    public void deleteRoom(Long id){
        RoomDTO dto  = roomCacheService.findById(id);

        if(dto != null) roomCacheService.delete(id);

        roomRepository.deleteById(id);  //상세보기 페이지에서 id값을 날리기 때문에 굳이 null체크 필요없을 거 같다
    }



    protected Room toEntity(RoomDTO dto){
        Room room = Room.builder()
                .hotelId(Long.parseLong(dto.getHotelId()))
                .description(dto.getDescription() != null ? dto.getDescription() : null)
                .roomPrice(dto.getRoomPrice() != null ? dto.getRoomPrice() : null)
                .roomLimit(dto.getRoomLimit() != null ? dto.getRoomLimit() : null)
                .roomNumber(dto.getRoomNumber() != null ? dto.getRoomNumber() : null)
                .roomClass(dto.getRoomClass() != null ? dto.getRoomClass() : null)
                .build();

        if(dto.getFileNames() != null){
            dto.getFileNames().forEach(room::addImage);
        }
        return room;
    }
    protected RoomDTO toDTO(Room room) {
        RoomDTO dto = RoomDTO.builder()
                .id(room.getId())
                .description(room.getDescription())
                .roomPrice(room.getRoomPrice())
                .roomLimit(room.getRoomLimit())
                .roomNumber(room.getRoomNumber())
                .roomClass(room.getRoomClass())
                .hotelId(Long.toString(room.getHotelId()))
                .build();

        dto.setFileNames(room.getRoomImage()
                .stream()

                .map(RoomImage::getName)
                .collect(Collectors.toList()));
        return dto;
    }

    public RoomResponseDTO getDetail(long id,String email){
        Room room = roomRepository.getRoomWithImage(id).orElse(null);
        boolean checking = false;
        if(room.getHotel().getWriter().equals(email != null? email : " ")) checking = true;

        if(room != null){
            RoomResponseDTO roomResponseDTO =  RoomResponseDTO.builder()
                    .id(room.getId())
                    .description(room.getDescription())
                    .roomClass(room.getRoomClass())
                    .roomLimit(room.getRoomLimit())
                    .hotelId(room.getHotelId())
                    .roomNumber(room.getRoomNumber())
                    .roomImage(room.getRoomImage()
                            .stream()
                            .map(RoomImage::getName)
                            .collect(Collectors.toUnmodifiableSet()))
                    .roomPrice(room.getRoomPrice())
                    .checking(checking)
                    .build();
            return roomResponseDTO;
        }else{
            return RoomResponseDTO.builder().build();
        }
    }




    public List<RoomResponseDTO> findAllWithImage(Long id){
        List<RoomResponseDTO> responseDTOList = new ArrayList<>();


        roomRepository.roomListWithImage(id).forEach(room -> {
            responseDTOList.add(RoomResponseDTO.builder()
                    .roomPrice(room.getRoomPrice())
                    .roomLimit(room.getRoomLimit())
                    .roomClass(room.getRoomClass())
                    .roomNumber(room.getRoomNumber())
                    .id(room.getId())
                    .roomImage(room.getRoomImage().stream()
                            .map(image -> image.getName())
                            .collect(Collectors.toUnmodifiableSet()))
                    .hotelId(room.getHotelId())
                    .description(room.getDescription())
                    .build());
        });

        return responseDTOList;
    }



    public boolean hotelInfoWriter(Long hotelId, String email){

        return roomRepository.checkingWriter(hotelId,email);
    }






}