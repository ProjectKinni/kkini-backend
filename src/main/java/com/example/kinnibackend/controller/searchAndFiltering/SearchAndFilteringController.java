package com.example.kinnibackend.controller.searchAndFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.service.product.ProductFilteringService;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final ProductFilteringService productFilteringService;

    // 검색과 검색 결과의 필터링 기능
    @GetMapping("/search")
    public ResponseEntity<List<ProductCardListResponseDTO>>
        searchAndFilterProducts(@ModelAttribute ProductFilteringResponseDTO productFilteringResponseDTO) {
        List<ProductCardListResponseDTO> searchResults =
                productFilteringService.filterProducts(productFilteringResponseDTO);
        return ResponseEntity.ok(searchResults);
    }

    // 자동완성 기능
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autoCompleteNames(@RequestParam String searchTerm) {
        return ResponseEntity.ok(searchService.autoCompleteNames(searchTerm));
    }

    // 상품리스트 -> 상품 상세 정보
    @GetMapping("/{productId}")
    public ResponseEntity<ProductCardListResponseDTO>
    getProductById(@PathVariable Long productId) {
        ProductCardListResponseDTO product = searchService.getProductById(productId);

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
