package com.example.kinnibackend.controller.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    // 검색 : 메인 -> 카테고리 or 상품 리스트
    @GetMapping("/products")
    public ResponseEntity<?> searchProductsByName(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryName) {
        List<ProductCardListResponseDTO> products = searchService.searchProductsByName(name, categoryName);

        if (products.isEmpty()) {
            return ResponseEntity.ok("해당 상품이 없습니다");
        }

        return ResponseEntity.ok(products);
    }

    // 자동완성 기능
    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autoCompleteNames(@RequestParam String name) {
        List<String> names = searchService.autoCompleteNames(name);
        return ResponseEntity.ok(names);
    }

    // 상품리스트 -> 끼니 or 끼니 카테고리 구분
    @GetMapping("/kkini-products/{type}")
    public ResponseEntity<List<ProductCardListResponseDTO>> getProductsByKkiniType(@PathVariable String type) {
        List<ProductCardListResponseDTO> products = searchService.getProductsByKkiniType(type);
        return ResponseEntity.ok(products);
    }

    // 상품 리스트 -> 카테고리 체크리스트
    @GetMapping("/products/categoryChecked")
    public ResponseEntity<?> searchProductsByCategory
            (@RequestParam String categoryName) {

        List<ProductCardListResponseDTO> products = searchService.filterProductsByCategory(categoryName);

        return ResponseEntity.ok(products);
    }

    // 상품 리스트 -> 필터 체크리스트
    @GetMapping("/products/filtered-products")
    public ResponseEntity<List<ProductCardListResponseDTO>> getFilteredProducts(
            @RequestParam String criteria) {

        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria(criteria);

        return ResponseEntity.ok(products);
    }

    // 상품리스트 -> 상품 상세 페이지
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductCardListResponseDTO> getProductById(@PathVariable Long productId) {
        ProductCardListResponseDTO product = searchService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}