package com.example.kinnibackend.service.review;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.kinnibackend.dto.review.*;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Review;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AmazonS3 naverS3Client;

    private final String BUCKET_NAME = "kkini-image-bucket";
    private final String FOLDER_NAME = "review-image";

    public CreateReviewResponseDTO createReview(CreateReviewRequestDTO createReviewRequestDTO, Long userId) {
        Users user = userRepository.findByUserId(userId);
        Product product = productRepository.findByProductId(createReviewRequestDTO.getProductId());

        MultipartFile[] images = {createReviewRequestDTO.getImage1(), createReviewRequestDTO.getImage2(), createReviewRequestDTO.getImage3(), createReviewRequestDTO.getImage4()};
        String[] imageUrls = new String[4];

        // 이미지를 S3에 업로드하고 URL을 가져옵니다.
        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            if (image != null) {
                String objectName = FOLDER_NAME + "/" + userId + "_" + createReviewRequestDTO.getProductId() + "_" + (i + 1);

                try {
                    File tempFile = File.createTempFile("image", ".jpg");
                    image.transferTo(tempFile);

                    naverS3Client.putObject(new PutObjectRequest(BUCKET_NAME, objectName, tempFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    imageUrls[i] = naverS3Client.getUrl(BUCKET_NAME, objectName).toString();

                } catch (Exception e) {
                    e.printStackTrace();
                    // 에러 처리 로직
                }
            }
        }

        Review review = createReviewRequestDTO.toEntity(user, product, imageUrls[0], imageUrls[1], imageUrls[2], imageUrls[3]);

        return CreateReviewResponseDTO.fromEntity(reviewRepository.save(review));
    }

    public List<GetReviewResponseDTO> getReviews(int page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        List<Review> reviews = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);

        return reviews.stream()
                .map(review -> GetReviewResponseDTO.fromEntity(review))
                .collect(Collectors.toList());
    }

    public List<GetReviewResponseDTO> getReviewsByProductId(Long productId, int page) {
        int pageSize = 10;
        Pageable pageable = (Pageable) PageRequest.of(page, pageSize);
        List<Review> reviews = reviewRepository.findByProduct_ProductIdOrderByCreatedAtDesc(productId, pageable);

        return reviews.stream()
                .map(review -> GetReviewResponseDTO.fromEntity(review))
                .collect(Collectors.toList());
    }

    public Page<GetReviewResponseDTO> getReviewsByUserId(Long userId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUsers_UserIdOrderByCreatedAtDesc(userId, pageable);
        return reviews.map(GetReviewResponseDTO::fromEntity);
    }

    public DeleteReviewResponseDTO deleteReviewByReviewId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        Long userId = review.getUsers().getUserId();
        Long productId = review.getProduct().getProductId();

        String objectName1 = FOLDER_NAME + "/" + userId + "_" + productId + "_" + 1;
        String objectName2 = FOLDER_NAME + "/" + userId + "_" + productId + "_" + 2;
        String objectName3 = FOLDER_NAME + "/" + userId + "_" + productId + "_" + 3;
        String objectName4 = FOLDER_NAME + "/" + userId + "_" + productId + "_" + 4;

        naverS3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, objectName1));
        naverS3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, objectName2));
        naverS3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, objectName3));
        naverS3Client.deleteObject(new DeleteObjectRequest(BUCKET_NAME, objectName4));

        reviewRepository.deleteById(reviewId);

        return DeleteReviewResponseDTO.fromEntity(reviewRepository.findById(reviewId));
    }

    public UpdateReviewResponseDTO updateReviewByReviewId(Long reviewId, CreateReviewRequestDTO updateInfo) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (!optionalReview.isPresent()) {
            return UpdateReviewResponseDTO.builder()
                    .message("리뷰가 존재하지 않습니다.")
                    .build();
        }

        Review review = optionalReview.get();
        MultipartFile[] images = {updateInfo.getImage1(), updateInfo.getImage2(), updateInfo.getImage3(), updateInfo.getImage4()};
        String[] imageUrls = new String[4];

        // 이미지를 S3에 업로드하고 URL을 가져옵니다.
        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            if (image != null) {
                String objectName = FOLDER_NAME + "/" + review.getUsers().getUserId() + "_" +
                        review.getProduct().getProductId() + "_" +
                        (i + 1);

                try {
                    File tempFile = File.createTempFile("image", ".jpg");
                    image.transferTo(tempFile);

                    naverS3Client.putObject(new PutObjectRequest(BUCKET_NAME, objectName, tempFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                    imageUrls[i] = naverS3Client.getUrl(BUCKET_NAME, objectName).toString();

                } catch (Exception e) {
                    e.printStackTrace();
                    // 에러 처리 로직
                }
            }
        }

        try {
            review.updateReview(updateInfo.getRating(), updateInfo.getContent(), imageUrls[0], imageUrls[1], imageUrls[2], imageUrls[3]);
            reviewRepository.save(review);
        } catch (Exception e) {
            return UpdateReviewResponseDTO.builder()
                    .message("리뷰 업데이트 실패")
                    .build();
        }

        return UpdateReviewResponseDTO.builder()
                .message("리뷰가 업데이트 되었습니다.")
                .build();
    }

    public boolean hasUserReviewedProduct(Long userId, Long productId) {
        List<Review> reviews = reviewRepository.findByUsers_UserIdAndProduct_ProductId(userId, productId);
        return !reviews.isEmpty();
    }


}
