package org.hotel.back.service;

import org.hotel.back.data.dto.FileDTO;
import org.hotel.back.data.dto.UploadDTO;
import org.hotel.back.data.response.FileResponseData;
import org.hotel.back.data.response.RoomDTO;

import java.util.List;
import java.util.Map;

public interface RoomService {

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


    public void save(RoomDTO roomDTO);

    public RoomDTO findByRoomWithImage(Long id);
}
