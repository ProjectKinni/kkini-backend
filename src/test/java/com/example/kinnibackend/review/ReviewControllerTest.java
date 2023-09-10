//package com.example.kinnibackend.review;
//
//import com.example.kinnibackend.dto.review.CreateReviewRequestDTO;
//import com.example.kinnibackend.dto.review.CreateReviewResponseDTO;
//import com.example.kinnibackend.entity.Product;
//import com.example.kinnibackend.entity.Users;
//import com.example.kinnibackend.repository.product.ProductRepository;
//import com.example.kinnibackend.repository.user.UserRepository;
//import com.example.kinnibackend.service.review.ReviewService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ReviewService reviewService;
//
//    @MockBean
//    private ProductRepository productRepository;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Test
//    public void testCreateReview() throws Exception {
//        String givenReviewContent = "Review Content for Test";
//        String givenProductName = "Product Name for Test";
//        String givenUserEmail = "kkini@kkini.com";
//        Product givenProduct = productRepository.save(Product.builder()
//                .productName(givenProductName)
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build());
//        Users givenUser = userRepository.save(Users.builder()
//                .email(givenUserEmail)
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build());
//        CreateReviewRequestDTO givenRequestDTO = CreateReviewRequestDTO.builder()
//                .userId(givenUser.getUserId())
//                .productId(givenProduct.getProductId())
//                .content(givenReviewContent)
//                .build();
//
//        CreateReviewResponseDTO responseDTO = CreateReviewResponseDTO.builder()
//                .message("리뷰가 등록되었습니다.")
//                .build();
//
//        when(reviewService.createReview(givenRequestDTO)).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/reviews")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(givenRequestDTO)))
//                .andExpect(status().isOk());
//    }
//}
//
