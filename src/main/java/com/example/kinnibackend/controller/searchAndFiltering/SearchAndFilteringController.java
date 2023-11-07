package com.example.kinnibackend.controller.searchAndFiltering;

import com.example.kinnibackend.dto.product.CombinedSearchFilterDTO;
import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.service.product.ProductFilterService;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class SearchAndFilteringController {

    @Autowired
    private final SearchService searchService;
    private final ReviewRepository reviewRepository;
    private final ProductFilterService productFilterService;

    static final Logger logger = LoggerFactory.getLogger(SearchAndFilteringController.class);

    @GetMapping("/search")
    public ResponseEntity<?> searchAndFilterProducts(
            @RequestParam(required = false) String searchTerm,
            @ModelAttribute CombinedSearchFilterDTO filters,
            @RequestParam(defaultValue = "0") int page) {
        logger.info("Product 검색 및 필터링 요청 받음, 검색어: {}, 페이지: {}", searchTerm, page);

        // 첫 번째 단계: 검색 및 자동완성
        Page<ProductCardListResponseDTO> searchResults = searchService.searchProducts(searchTerm, page);

        // 필터링이 요청되었는지 확인 (CombinedSearchFilterDTO로 변경됨)
        if (filters != null && filters.hasFilters()) {
            List<ProductCardListResponseDTO> filteredPage = productFilterService.filterProducts(filters, page);
            return ResponseEntity.ok(filteredPage);
        }
        return ResponseEntity.ok(searchResults);
    }

    // 상품리스트 -> 상품 상세 정보
    @GetMapping("/{productId}")
    public ResponseEntity<ProductCardListResponseDTO>
    getProductById(@PathVariable Long productId) {
        ProductCardListResponseDTO product = searchService.getProductById(productId);
        product.setReviewCount(reviewRepository.findTotalReviewCountByProductId(productId));

        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // 조회수 증가
    @PostMapping("/{productId}/viewCount")
    public ResponseEntity<Map<String, String>>
        incrementViewCount(@PathVariable Long productId, @RequestParam Long userId) {
            searchService.incrementViewCount(productId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "View count incremented");
            return ResponseEntity.ok(response);
    }
}
