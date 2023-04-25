package org.hotel.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.data.dto.MemberDTO;
import org.hotel.back.data.response.RoomDTO;
import org.hotel.back.data.response.RoomResponseDTO;
import org.hotel.back.domain.Room;
import org.hotel.back.service.RoomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.events.Event;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room/list")
    public String roomListGET(@RequestParam Long id,
                              String hotelNa,
                              Model model,
                              @AuthenticationPrincipal MemberDTO memberDTO){
            log.info("HOTEL ROOM LIST NY NAME {}",hotelNa);
            if(memberDTO != null) model.addAttribute("email",memberDTO.getEmail());
            model.addAttribute("hotelNa",hotelNa);
            model.addAttribute("dto",roomService.findAllWithImage(id));
            model.addAttribute("hotelId",id);
            return "room/room-list";
    }



    // /room/detail?id=1&hotelNa=소풍호텔
    @GetMapping("/room/detail")
    public String roomDetailsGET(Long id,
                                 Model model,
                                 String hotelNa,
                                 @RequestParam(required = false) String check,
                                 String hotel,
                                 @AuthenticationPrincipal MemberDTO memberDTO){
        RoomResponseDTO data =roomService.getDetail(id,memberDTO != null ? memberDTO.getEmail() : null);
        if(data != null) data.setHotelNa(hotelNa);


        model.addAttribute("dto",data);
        model.addAttribute("hotelId",hotel);
        if(check != null) model.addAttribute("check",check);
        return "room/room-detail";
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/room/save")
    public String roomSaveGET(Long id,
                              String hotelNa,
                              Model model){

        model.addAttribute("hotelNa",hotelNa);
        model.addAttribute("hotelId", id);
        return "room/room-save";
    }


    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/room/modify")
    public String roomModifyGET(Long id,
                                String hotelNa,
                                Model model,
                                @AuthenticationPrincipal MemberDTO memberDTO){

        var responseData =roomService.getDetail(id,memberDTO.getEmail());
        responseData.setHotelNa(hotelNa);
        model.addAttribute("dto",responseData);
        if(responseData.isChecking()){
                model.addAttribute("check",true);
        }
        return "room/room-modify";
    }


    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/room/delete")
    public String roomDeleteGET(String id,
                                String hotel,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal MemberDTO memberDTO){
            redirectAttributes.addAttribute("id",hotel);
            boolean authorize =
                    roomService.hotelInfoWriter(Long.parseLong(hotel),memberDTO != null ? memberDTO.getEmail() : null);
            if(authorize) roomService.deleteRoom(Long.parseLong(id));
            else return  "redirect:/room/detail?id="+id+"&check=err";
        return "redirect:/room/list";
    }

   // @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/room/save")
    public String roomSavePOST(RoomDTO roomDTO){
            log.info("==> {}",roomDTO);
            roomService.save(roomDTO);
             return "redirect:/hotel/detail&id="+roomDTO.getHotelId();
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/room/modify")
    public String roomModifyPOST(RoomDTO roomDTO,RedirectAttributes redirectAttributes){
        System.out.println(roomDTO);
        roomService.modifyRoom(roomDTO);
        redirectAttributes.addAttribute("id",roomDTO.getId());
        return "redirect:/room/detail";
    }

}
