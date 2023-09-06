package com.example.kinnibackend.controller.searchAndFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.productFiltering.ProductFilteringService;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class SearchAndFilteringController {

    @Autowired
    private final SearchService searchService;

    @Autowired
    private final ProductFilteringService productFilteringService;

    // 검색결과의 필터링 기능
    @GetMapping("/search")
    public ResponseEntity<List<ProductCardListResponseDTO>> searchAndFilterProducts(
            @RequestParam String searchTerm,
            @RequestParam(required = false) Boolean isGreen,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Boolean isLowCalorie,
            @RequestParam(required = false) Boolean isSugarFree,
            @RequestParam(required = false) Boolean isLowSugar,
            @RequestParam(required = false) Boolean isLowCarb,
            @RequestParam(required = false) Boolean isKeto,
            @RequestParam(required = false) Boolean isTransFat,
            @RequestParam(required = false) Boolean isHighProtein,
            @RequestParam(required = false) Boolean isLowSodium,
            @RequestParam(required = false) Boolean isCholesterol,
            @RequestParam(required = false) Boolean isSaturatedFat,
            @RequestParam(required = false) Boolean isLowFat
    ) {

        List<ProductCardListResponseDTO> searchResults =
                productFilteringService.filterProducts(isGreen, searchTerm, categoryName,
                        isLowCalorie, isSugarFree, isLowSugar, isLowCarb, isKeto, isTransFat,
                        isHighProtein, isLowSodium, isCholesterol, isSaturatedFat, isLowFat
                );

        return ResponseEntity.ok(searchResults);
    }

    // 자동완성 기능
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autoCompleteNames(@RequestParam String searchTerm) {
        return ResponseEntity.ok(searchService.autoCompleteNames(searchTerm));
    }

    // 상품리스트 -> 상품 상세 정보
    @GetMapping("/{productId}")
    public ResponseEntity<ProductCardListResponseDTO> getProductById(@PathVariable Long productId) {
        ProductCardListResponseDTO product = searchService.getProductById(productId);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
}
