package org.hotel.back.service;

import org.hotel.back.data.request.ReviewRequestDTO;
import org.hotel.back.data.response.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    public ReviewResponseDTO readReview(Long id);

    public List<ReviewResponseDTO> findReview(Long id);

    public Long saveReview(Long id, ReviewRequestDTO reviewRequestDTO);

    public Long updateReview(ReviewRequestDTO reviewRequestDTO);

    public boolean deleteReview(Long id);
}
