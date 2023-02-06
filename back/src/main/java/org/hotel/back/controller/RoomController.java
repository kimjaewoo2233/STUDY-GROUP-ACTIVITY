package org.hotel.back.controller;


import lombok.RequiredArgsConstructor;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @GetMapping("/room/save")
    public String roomSaveGET(RoomDTO roomDTO){
        return "/room/room-save";
    }

    @PostMapping("/room/saved")
    public String roomSavePOST(RoomDTO roomDTO){
            roomService.save(roomDTO);
             return "redirect:/room/save";
    }

}
