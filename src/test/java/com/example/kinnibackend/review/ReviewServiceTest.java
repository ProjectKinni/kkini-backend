package com.example.kinnibackend.review;

import com.example.kinnibackend.dto.review.CreateReviewRequestDTO;
import com.example.kinnibackend.dto.review.CreateReviewResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import com.example.kinnibackend.service.review.ReviewService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback
    public void createReviewTest() {
        //given
        String givenReviewContent = "Review Content for Test";
        String givenProductName = "Product Name for Test";
        String givenUserEmail = "kkini@kkini.com";
        Product givenProduct = productRepository.save(Product.builder()
                .productName(givenProductName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        Users givenUser = userRepository.save(Users.builder()
                .email(givenUserEmail)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        CreateReviewRequestDTO givenReviewDTO = CreateReviewRequestDTO.builder()
                .userId(givenUser.getUserId())
                .productId(givenProduct.getProductId())
                .content(givenReviewContent)
                .build();

        //when
        CreateReviewResponseDTO responseDTO = reviewService.createReview(givenReviewDTO);

        //then
        assertThat(responseDTO).isNotNull();
    }


}
