package org.hotel.back.repository;

import org.hotel.back.domain.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room,Long> {


    @EntityGraph(attributePaths = {"roomImage"})
    @Query("SELECT r FROM Room r WHERE r.id = :id")
    public Room getRoomWithImage(@Param("id") Long id);
}
