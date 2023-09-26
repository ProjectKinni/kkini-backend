package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.productViewCount.ProductViewCount;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Autowired
    private final ProductRepository productRepository;
    private final ProductViewCountRepository productViewCountRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // 자동 완성 기능
    public List<String> autoCompleteNames(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            throw new InvalidSearchTermException("검색어를 입력해주세요.");
        }

        String modifiedName = searchTerm.replace(" ", "%"); // 공백을 %로 대체

        // 상품명
        List<String> productNames = new ArrayList<>();
        for (Product product : productRepository.findByProductName(modifiedName)) {
            productNames.add(product.getProductName());
        }

        // 카테고리명
        List<String> categoryNames = new ArrayList<>();
        for (Product product : productRepository.findByCategoryName(modifiedName)) {
            categoryNames.add(product.getCategoryName());
        }

        // 상품명과 카테고리명을 결합
        List<String> combinedNames = new ArrayList<>();
        combinedNames.addAll(productNames);
        combinedNames.addAll(categoryNames);

        // 중복 제거
        List<String> distinctNames = new ArrayList<>();
        for (String name : combinedNames) {
            if (!distinctNames.contains(name)) {
                distinctNames.add(name);
            }
        }
        return distinctNames;
    }

    // 상품 리스트 -> 상품 상세
    public ProductCardListResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        // 조회수 계산 로직
        Long totalViewCount = productViewCountRepository.findTotalViewCountByProductId(productId);
        // 리뷰수 계산 로직
        Long totalReviewCount = reviewRepository.findTotalReviewCountByProductId(productId);

        ProductCardListResponseDTO responseDTO = ProductCardListResponseDTO.fromEntity(product);
        responseDTO.setViewCount(totalViewCount);
        responseDTO.setReviewCount(totalReviewCount);

        return responseDTO;
    }

    public void incrementViewCount(Long productId, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        if (userId != null) {
            Users user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                ProductViewCount productViewCount =
                        productViewCountRepository.findByProductProductIdAndUsersUserId(productId, userId)
                                .orElseGet(() -> ProductViewCount.builder()
                                        .product(product)
                                        .users(user)
                                        .viewCount(0L)
                                        .build());

                productViewCount.incrementViewCount();
                productViewCountRepository.save(productViewCount);
            }
        }
    }

}