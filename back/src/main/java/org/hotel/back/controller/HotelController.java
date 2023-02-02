package org.hotel.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.domain.Hotel;

import org.hotel.back.domain.Review;
import org.hotel.back.dto.request.HotelRequestDTO;
import org.hotel.back.dto.request.ReviewRequestDTO;
import org.hotel.back.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    @GetMapping("/hotel/save") 
    public String hotelWriteForm(){

        return "hotelSave";
    }
//==========호텔 저장==============
    @PostMapping("/hotel/save")
    public String hotelSave(HotelRequestDTO hotelRequestDTO){
        hotelService.write(hotelRequestDTO);
        System.out.println(hotelRequestDTO);
        return "redirect:/hotel";
    }

    @GetMapping("/hotel")
    public String hotelList(@PageableDefault(page = 0, size = 10,sort="id",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        //서비스에서 생성한 리스트를 list라는 이름으로 반환하겠다.
        Page<Hotel> list =hotelService.hotelList(pageable);
        int nowPage =list.getPageable().getPageNumber()+1;//pageable은 0부터 시작해서 +1을 해줘야 함
        int startPage=1;//시작페이지
        int endPage=list.getTotalPages();//10개씩 자른 페이지


        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        model.addAttribute("list",list);
        return "hotelMain";
    }
//========호텔 자세히보기  ============
    @GetMapping("/hotel/detail")
    public String hotelDetail(Model model, Long id){
        Hotel hotel=hotelService.hotelDetail(id); //호텔 객체를 불러옴 ->service hotelDetail메서드
        List<Review> reviewlist =hotel.getReviews(); //호텔 객체에서 review 가져와서 넣음(hotelId의 리뷰)
        model.addAttribute("article",hotel);
        model.addAttribute("review",reviewlist);
        return "hotelDetail";
    }
//===========호텔 지우기===============
    @GetMapping("/hotel/delete")
    public String hotelDelete(Long id){
        hotelService.hotelDelete(id);
        return "redirect:/hotel";
    }
//=============호텔 업데이트==============
    @PostMapping("/hotel/update")
    public String hotelUpdatePost(HotelRequestDTO hotelRequestDTO){
        hotelService.hotelUpdate(hotelRequestDTO);
        return "redirect:/hotel/detail?id="+hotelRequestDTO.getId(); //숙소정보 업데이트 후 detail=id로 다시 redirect
    }

    @GetMapping("/hotel/update")
    public String hotelUpdate(Long id, Model model){
        model.addAttribute("article",hotelService.hotelDetail(id));
        return "hotelUpdate";
    }
}
