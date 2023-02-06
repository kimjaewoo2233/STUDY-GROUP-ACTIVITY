package org.hotel.back.service.Impl;

import lombok.RequiredArgsConstructor;
import org.hotel.back.domain.Hotel;
import org.hotel.back.domain.Review;
import org.hotel.back.data.request.ReviewRequestDTO;
import org.hotel.back.data.response.ReviewResponseDTO;
import org.hotel.back.repository.HotelRepository;
import org.hotel.back.repository.ReviewRepository;
import org.hotel.back.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Long saveReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        Hotel hotel = hotelRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException
                        ("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));
        reviewRequestDTO.setHotel(hotel);
        System.out.println(reviewRequestDTO);
        Review review = reviewRequestDTO.toEntity(reviewRequestDTO);
        System.out.println("=======**===="+review.getHotel());
        reviewRepository.save(review);
        return reviewRequestDTO.getId();
    }



    @Override
    public List<ReviewResponseDTO> findReview(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 호텔이 존재하지 않습니다."));
        List<Review> reviews = hotel.getReviews();
        return reviews.stream().map(ReviewResponseDTO::new).collect(Collectors.toList());

    }
    @Override
    public ReviewResponseDTO readReview(Long id){

        Review review=reviewRepository.findById
                (id).orElseThrow(RuntimeException::new);
        System.out.println("======="+review);
        return new ReviewResponseDTO(review);

    }

    public Long updateReview(ReviewRequestDTO reviewRequestDTO){
        System.out.println("****reviewRequestDTO*******  "+reviewRequestDTO);
        Review review=reviewRepository.findById(reviewRequestDTO.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. "));
        review.updateReview(reviewRequestDTO.getReviewContent());
        reviewRepository.save(review);
        return review.getHotel().getId();
    }

    public boolean deleteReview(Long id){
        reviewRepository.deleteById(id);
        return true;
    }
}
