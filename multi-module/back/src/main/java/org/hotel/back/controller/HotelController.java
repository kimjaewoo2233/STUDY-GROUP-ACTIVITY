package org.hotel.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.data.response.HotelListResponseDTO;
import org.hotel.back.data.response.HotelResponseDTO;
import org.hotel.back.data.response.KaKaoResponseData;

import org.hotel.back.data.request.HotelRequestDTO;
import org.hotel.back.domain.Hotel;
import org.hotel.back.service.HotelService;
import org.hotel.back.service.api.KaKaoAPIService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    private final KaKaoAPIService kaKaoAPIService;
    @Value("${upload.path}")
    private String path;


    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/hotel/save")//localhost:8080/save
    public String hotelWriteForm(){

        return "hotel/hotelSave";
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/hotel/save")
    public String hotelSave(HotelRequestDTO hotelRequestDTO) throws ParseException {
        String address=hotelRequestDTO.getAddress();
        try{
            if(kaKaoAPIService.getLocationInfo(address).isPresent()){
                KaKaoResponseData kaKaoResponseData= kaKaoAPIService.getLocationInfo(hotelRequestDTO.getAddress()).orElse(null);
                hotelRequestDTO.setLatitude(kaKaoResponseData.getLatitude());
                hotelRequestDTO.setLongitude(kaKaoResponseData.getLongitude());
            }
        }catch (IndexOutOfBoundsException e){
            return "redirect:/hotel/save";
        }
        hotelService.write(hotelRequestDTO);

        return "redirect:/hotel";
    }

    @GetMapping("/hotel")
    public String hotelList(@PageableDefault(page = 0, size = 12,sort="id",direction = Sort.Direction.DESC) Pageable pageable,
                            Model model,
                            @RequestParam(required = false) String login){
        if(login != null) model.addAttribute("msg","로그인 성공!");

        //서비스에서 생성한 리스트를 list라는 이름으로 반환하겠다.
        Page<HotelListResponseDTO> list =hotelService.hotelList(pageable);

        int nowPage =list.getPageable().getPageNumber()+1;//pageable은 0부터 시작해서 +1을 해줘야 함
        int startPage=1;//시작페이지
        int endPage=list.getTotalPages();//12개씩 자른 페이지

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("list",list);
        return "hotel/index";
    }


    //========호텔 자세히보기  ============
    @GetMapping("/hotel/detail")
    public String hotelDetail(Model model, Long id) throws ParseException {
        HotelResponseDTO hotelResponseDTO =hotelService.hotelDetail(id); //호텔 객체를 불러옴 ->service hotelDetail메서드
        model.addAttribute("article",hotelResponseDTO);
        model.addAttribute("path",path);
        return "hotel/hotelDetail";
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/hotel/delete")
    public String hotelDelete(Long id) {
        hotelService.hotelDelete(id);
        return "redirect:/hotel";
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/hotel/update")
    public String hotelUpdatePost(HotelRequestDTO hotelRequestDTO) {
        String updateAddress=hotelRequestDTO.getAddress();
        try{
            if(kaKaoAPIService.getLocationInfo(updateAddress).isPresent()){
                KaKaoResponseData kaKaoResponseData= kaKaoAPIService.getLocationInfo(hotelRequestDTO.getAddress()).orElse(null);
                hotelRequestDTO.setLatitude(kaKaoResponseData.getLatitude());
                hotelRequestDTO.setLongitude(kaKaoResponseData.getLongitude());
            }
        }catch (IndexOutOfBoundsException e){
            return "redirect:/hotel/update?id="+hotelRequestDTO.getId();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        hotelService.hotelUpdate(hotelRequestDTO);
        return "redirect:/hotel/detail?id=" + hotelRequestDTO.getId(); //숙소정보 업데이트 후 detail=id로 다시 redirect
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/hotel/update")
    public String hotelUpdate(Long id, Model model) throws ParseException {
        model.addAttribute("article", hotelService.hotelDetail(id));


        return "hotel/hotelUpdate";
    }
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/image/delete")
    public String imageDelete(String name,Long id) throws ParseException {
        hotelService.imageDelete(name);

        return "redirect:/hotel/update?id="+id;
    }

}
