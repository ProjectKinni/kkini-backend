package com.example.kinnibackend.dto.review;

import com.example.kinnibackend.entity.Review;
import lombok.*;

@Getter @Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateReviewResponseDTO {
    private String message;

    public static CreateReviewResponseDTO fromEntity(Review review) {
        return CreateReviewResponseDTO.builder()
                .message(review == null ? "리뷰가 등록되었습니다." : "리뷰 등록 실패")
                .build();
    }
}
