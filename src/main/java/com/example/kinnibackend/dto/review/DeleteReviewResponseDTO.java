package com.example.kinnibackend.dto.review;

import com.example.kinnibackend.entity.Review;
import lombok.*;

import java.util.Optional;

@Getter @Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DeleteReviewResponseDTO {
    private String message;

    public static DeleteReviewResponseDTO fromEntity(Optional<Review> review) {
        return DeleteReviewResponseDTO.builder()
                .message(review.isEmpty() ? "리뷰가 삭제되었습니다." : "리뷰 삭제 실패")
                .build();
    }

}
