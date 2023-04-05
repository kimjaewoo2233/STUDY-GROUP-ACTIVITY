package org.hotel.back.service;

import org.hotel.back.data.response.HotelListResponseDTO;
import org.hotel.back.data.response.HotelResponseDTO;
import org.hotel.back.domain.Hotel;
import org.hotel.back.data.request.HotelRequestDTO;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface HotelService {



    boolean write(HotelRequestDTO hotelRequestDTO);



    /**
     * @param Hotel PK를 받음
     * @apiNote 위도,경도를 통해 도로명주소로 변경한 DTO를 리턴한다.
     * */

    Page<HotelListResponseDTO>  hotelList(Pageable pageable);

    HotelResponseDTO hotelDetail(Long id) throws ParseException;
    boolean  hotelDelete(Long id);
    boolean hotelUpdate(HotelRequestDTO hotelRequestDTO);
    List<HotelResponseDTO> hotelListSearch(String keyword);
    boolean imageDelete(String name);



}
