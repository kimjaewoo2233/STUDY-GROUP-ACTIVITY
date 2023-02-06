package org.hotel.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.data.request.ReviewRequestDTO;
import org.hotel.back.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    //-----------댓글 삭제
    @GetMapping("/hotel/review/delete")
    public String reviewDelete(Long id, Long hotelid){
        reviewService.deleteReview(id);
        return "redirect:/hotel/detail?id="+hotelid;
    }

    //----------댓글 작성
    @PostMapping("/hotel/review/save")
    public String reviewSave(Long id, ReviewRequestDTO reviewRequestDTO){
        System.out.println(id);
        System.out.println(reviewRequestDTO);
        reviewService.saveReview(id,reviewRequestDTO);
        return "redirect:/hotel/detail?id="+id;
    }

    //-----------댓글 수정
    @GetMapping("/hotel/review/update")
    public String reviewUpdate(Long id, Model model){
        model.addAttribute("review",reviewService.readReview(id));
        return "reviewUpdate";
    }

    @PostMapping("/hotel/review/update")
    public String reviewUpdatePost(ReviewRequestDTO reviewRequestDTO){
        System.out.println("----=========***"+reviewRequestDTO);
        long id=reviewService.updateReview(reviewRequestDTO);
        System.out.println(reviewRequestDTO.getReviewContent());
        return "redirect:/hotel/detail?id="+id;
    }


}
