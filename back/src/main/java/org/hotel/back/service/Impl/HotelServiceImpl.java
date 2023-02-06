package org.hotel.back.service.Impl;

import lombok.RequiredArgsConstructor;
import org.hotel.back.config.exception.FileUploadException;
import org.hotel.back.config.exception.FileViewException;
import org.hotel.back.data.dto.FileDTO;
import org.hotel.back.data.dto.HotelImageDTO;
import org.hotel.back.data.dto.UploadDTO;
import org.hotel.back.data.response.FileResponseData;
import org.hotel.back.data.response.HotelResponseDTO;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.domain.Hotel;
import org.hotel.back.data.request.HotelRequestDTO;
import org.hotel.back.domain.HotelImage;
import org.hotel.back.domain.Room;
import org.hotel.back.domain.RoomImage;
import org.hotel.back.repository.HotelImageRepository;
import org.hotel.back.repository.HotelRepository;
import org.hotel.back.service.HotelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelImageRepository hotelImageRepository;


    @Value("${upload.path}")
    private String path;
    //호텔 저장
    @Override
    public boolean write(HotelRequestDTO hotelRequestDTO) throws IOException{
        //파일 첨부 여부에 따라 로직분리
        if(hotelRequestDTO.getHotelImage().isEmpty()){ //첨부파일 없음
            Hotel hotel=hotelRequestDTO.toEntity(hotelRequestDTO);
            //요청 데이터를(hotelRequestDTO) hotel객체로 바꿔준다.
            hotelRepository.save(hotel);
            System.out.println("첨부파일 없음 ");
        }
        else{
            Hotel hotel=hotelRequestDTO.toEntity(hotelRequestDTO);
            Long hotelId=hotelRepository.save(hotel).getId();
            for(MultipartFile hotelImage:hotelRequestDTO.getHotelImage()){;
                String uuid = UUID.randomUUID().toString()+"_"+hotelImage.getOriginalFilename();
                Path savePath = Paths.get(path, uuid);
                try{
                    hotelImage.transferTo(savePath);
                } catch (IOException e) {
                    throw new FileUploadException();
                }
                Hotel findHotel=hotelRepository.findById(hotelId).get();
                HotelImage hotelImageSave=HotelImage.builder().name(uuid).hotel(findHotel).build();
                hotelImageRepository.save(hotelImageSave);
            }
        }
        return true;
    }
    //호텔 리스트
    @Override
    public Page<Hotel> hotelList(Pageable pageable) {

        return hotelRepository.findAll(pageable);
    }
    //호텔 자세히보기
    @Override
    public Hotel hotelDetail(Long id) {
        Hotel hotel=hotelRepository.findJoinImages(id);

        return hotel;
    }
    //호텔 지우기
    @Override
    public boolean hotelDelete(Long id) {
        hotelRepository.deleteById(id);
        return true;
    }
    //호텔 업데이트
    @Override
    public boolean hotelUpdate(HotelRequestDTO hotelRequestDTO) {
        Hotel hotel=hotelRepository.findById(hotelRequestDTO.getId()).get();
        hotel.modifyHotel(hotelRequestDTO.getHotelName(),
                hotelRequestDTO.getCityName(),
                hotelRequestDTO.getTellNumber(),
                hotelRequestDTO.getLatitude(),
                hotelRequestDTO.getLongitude());
        hotelRepository.save(hotel);
        return true;
    }

    @Override
    public List<Hotel> hotelListSearch(String keyword) {
        List<Hotel> hotels = hotelRepository.findByHotelNameContaining(keyword);
        return hotels;
    }

    @Override
    public List<FileDTO> upload(UploadDTO uploadDTO) {
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

    @Override
    public FileResponseData viewFile(String fileName) {
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

    @Override
    public Map<String, Boolean> removeFile(String fileName) {
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

    @Override
    public HotelResponseDTO findByRoomWithImage(Long id) {
        return null;
    }


}
