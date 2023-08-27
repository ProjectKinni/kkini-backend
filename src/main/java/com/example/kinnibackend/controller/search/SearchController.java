package com.example.kinnibackend.controller.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/products")
    public ResponseEntity<?> searchProductsByNameAndCategory(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName) {
        return createResponse(searchService.searchProductsByNameAndCategory(productName, categoryName), "해당 상품이 없습니다");
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autoCompleteNames(@RequestParam String name) {
        return ResponseEntity.ok(searchService.autoCompleteNames(name));
    }

    @GetMapping("/kkini-products/{isKkini}")
    public ResponseEntity<List<ProductCardListResponseDTO>> getProductsByKkiniType(@PathVariable boolean isKkini) {
        return ResponseEntity.ok(searchService.getProductsByKkiniType(isKkini));
    }

    @GetMapping("/products/categoryChecked")
    public ResponseEntity<?> searchProductsByCategory(@RequestParam String categoryName) {
        return createResponse(searchService.filterProductsByCategory(categoryName), "해당 카테고리의 상품이 없습니다");
    }

    @GetMapping("/products/filtered-products")
    public ResponseEntity<List<ProductCardListResponseDTO>> getFilteredProducts(@RequestParam String criteria) {
        return ResponseEntity.ok(searchService.filterProductsByCriteria(criteria));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductCardListResponseDTO> getProductById(@PathVariable Long productId) {
        ProductCardListResponseDTO product = searchService.getProductById(productId);
        return (product != null) ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    private <T> ResponseEntity<?> createResponse(List<T> data, String emptyMessage) {
        if (data.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("message", emptyMessage));
        }
        return ResponseEntity.ok(data);
    }
}