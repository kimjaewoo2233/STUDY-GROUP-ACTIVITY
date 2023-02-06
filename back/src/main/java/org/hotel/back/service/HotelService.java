package org.hotel.back.service;

import org.hotel.back.data.dto.FileDTO;
import org.hotel.back.data.dto.UploadDTO;
import org.hotel.back.data.response.FileResponseData;
import org.hotel.back.data.response.HotelResponseDTO;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.domain.Hotel;
import org.hotel.back.data.request.HotelRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface HotelService {
    public boolean write(HotelRequestDTO hotelRequestDTO) throws IOException;
    public Page<Hotel> hotelList(Pageable pageable);
    public Hotel hotelDetail(Long id);
    public boolean  hotelDelete(Long id);
    public boolean hotelUpdate(HotelRequestDTO hotelRequestDTO);
    public List<Hotel> hotelListSearch(String keyword);
    /**
     * @param uploadDTO 필드 fileList name을 맞춰서 이미지를 보내주면 받을 수 있음
     * @apiNote uuid_파일명 으로 받고 이미지만 받을 수 있도록 만듬
     * */
    public List<FileDTO> upload(UploadDTO uploadDTO);

    /**
     * @param fileName 원하는 파일 이름을 보낸다.
     * @return FileResponseData 타입을 설명하는 header와 이미지 리소스를 리턴한다
     * */
    public FileResponseData viewFile(String fileName);


    /**
     *
     * @param fileName 삭제할 파일의 이름 받음
     * @return json 데이터로 보내기 때문에 Map 타입으로 리턴한다.
     */
    public Map<String,Boolean> removeFile(String fileName);

    public HotelResponseDTO findByRoomWithImage(Long id);


}
