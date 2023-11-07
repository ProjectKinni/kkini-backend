package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.ProductFilterCriteria;
import com.example.kinnibackend.entity.productViewCount.ProductViewCount;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;
    private final ProductViewCountRepository productViewCountRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    // 검색과 자동완성 기능
    public Page<ProductCardListResponseDTO> searchProducts(String input, int page) {
        logger.info("searchAndAutoComplete 메소드 시작");

        // 입력값 처리
        String searchTerm = input != null ? input.trim() : "";
        if (searchTerm.isEmpty()) {
            logger.warn("searchTerm이 비어있습니다.");
            return Page.empty(); // 또는 다른 방식으로 처리
        }

        // 페이지 설정
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 검색어가 주어진 경우, 자동 완성 로직을 적용해 검색 실행
        Page<Product> searchResults = productRepository.findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                searchTerm.replace(" ", "%"), searchTerm.replace(" ", "%"), pageable);

        if (searchResults.isEmpty()) {
            logger.info("검색 결과가 없습니다.");
            return Page.empty(pageable);
        }

        // 결과를 DTO로 변환
        Page<ProductCardListResponseDTO> responsePage = searchResults.map(ProductCardListResponseDTO::fromEntity);

        logger.info("searchAndAutoComplete 메소드 종료, 결과 개수: {}", responsePage.getTotalElements());
        return responsePage;
    }

    // 검색 결과 필터링 메소드
    public Page<ProductCardListResponseDTO> filterSearchResults(
            List<ProductCardListResponseDTO> allResults,
            ProductFilterCriteria criteria,
            Pageable pageable) {

        // 결과를 필터링하는 로직
        List<ProductCardListResponseDTO> filteredResults = allResults.stream()
                .filter(dto -> dto.matchesCriteria(criteria)) // matchesCriteria 메소드로 필터링
                .collect(Collectors.toList());

        // 페이징 처리를 위한 준비
        int totalFiltered = filteredResults.size(); // 'long' 대신 'int'를 사용해야 할 수 있습니다.
        int start = (int) pageable.getOffset(); // 'long'에서 'int'로 캐스팅
        int end = (int) Math.min((start + pageable.getPageSize()), totalFiltered); // 'long'에서 'int'로 캐스팅

        // 페이지 요청에 맞는 sublist를 생성
        List<ProductCardListResponseDTO> pageSubList = filteredResults
                .subList(start, end); // 여기서 'int' 타입을 사용합니다.

        // Page 객체를 생성하여 반환
        return new PageImpl<>(pageSubList, pageable, totalFiltered);
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