//package com.example.kinnibackend.review;
//
//import com.example.kinnibackend.entity.Product;
//import com.example.kinnibackend.entity.Review;
//import com.example.kinnibackend.entity.Users;
//import com.example.kinnibackend.repository.product.ProductRepository;
//import com.example.kinnibackend.repository.review.ReviewRepository;
//import com.example.kinnibackend.repository.user.UserRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//public class ReviewRepositoryTest {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @Rollback
//    public void testCreateReview() {
//        // Given
//        Double givenRating = 4.5;
//        String givenProductName = "Product A";
//        String givenUsersEmail = "userA@kkini.com";
//
//        Review review = Review.builder()
//                .product(productRepository.save(
//                                Product.builder()
//                                        .productId(1L)
//                                        .productName(givenProductName)
//                                        .averageRating(4.0F)
//                                        .createdAt(LocalDateTime.now())
//                                        .updatedAt(LocalDateTime.now())
//                                        .build()
//                        )
//                )
//                .users(userRepository.save(
//                                Users.builder()
//                                        .userId(1L)
//                                        .email(givenUsersEmail)
//                                        .createdAt(LocalDateTime.now())
//                                        .updatedAt(LocalDateTime.now())
//                                        .build()
//                        )
//                )
//                .rating(givenRating)
//                .content("Great product!")
//                .build();
//
//        // When
//        Review savedReview = reviewRepository.save(review);
//
//        // Then
//        assertThat(savedReview.getRating()).isEqualTo(givenRating);
//        assertThat(savedReview.getProduct().getProductName()).isEqualTo(givenProductName);
//        assertThat(savedReview.getUsers().getEmail()).isEqualTo(givenUsersEmail);
//    }
//
//}
