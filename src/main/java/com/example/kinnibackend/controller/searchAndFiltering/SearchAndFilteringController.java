package com.example.kinnibackend.controller.searchAndFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.ProductFilterCriteria;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    static final Logger logger = LoggerFactory.getLogger(SearchAndFilteringController.class);

    @GetMapping("/search")
    public ResponseEntity<Page<ProductCardListResponseDTO>> searchAndFilterProducts(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            ProductFilterCriteria criteria) {

        // searchTerm이 비어 있거나 null인 경우 적절한 처리 수행
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            // 여기서는 단순히 빈 ResponseEntity를 반환하지만,
            // 필요에 따라 다른 처리를 수행할 수 있습니다.
            return ResponseEntity.ok(Page.empty());
        }

        // 검색 기능 수행
        Page<ProductCardListResponseDTO> searchResults = searchService.searchProducts(searchTerm, page);
        if (searchResults.isEmpty()) {
            // 검색 결과가 없는 경우, ResponseEntity를 사용하여 상태와 함께 빈 페이지 반환
            return ResponseEntity.ok(Page.empty());
        }

        // 전체 검색 결과를 가져와서 필터링
        List<ProductCardListResponseDTO> allSearchResults = searchResults.getContent();
        Pageable pageable = searchResults.getPageable();
        Page<ProductCardListResponseDTO> filteredResults = searchService.filterSearchResults(allSearchResults, criteria, pageable);

        // 필터링된 결과와 함께 ResponseEntity 반환
        return ResponseEntity.ok(filteredResults);
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
