package org.hotel.back.repository;

import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.HotelImage;
import org.hotel.back.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    @Query("select distinct h from Hotel h left join fetch h.reviews where h.id = :id")
    Hotel findFetchJoin(@Param("id")Long id);
    //fetch join-> review테이블에서 한 번 더 쿼리가 일어나는 것을 방지
    //호텔 id에 저장되어있는 리뷰를 불러옴

    @Query("select distinct h from Hotel AS h LEFT JOIN Review AS r ON h.id=r.hotel.id LEFT JOIN HotelImage AS i ON h.id=i.hotel.id where h.id= :id")
    Hotel findJoinImages(@Param("id")Long id);

    List<Hotel> findByHotelNameContaining(String keyword);

}
