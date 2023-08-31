package com.example.kinnibackend.controller.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SearchController {

    @Autowired
    private final SearchService searchService;

    // 검색
    @GetMapping("/")
    public ResponseEntity<List<ProductCardListResponseDTO>> searchProducts(@RequestParam String searchTerm) {
        List<ProductCardListResponseDTO> products = searchService.searchProducts(searchTerm);
        return ResponseEntity.ok(products);
    }

    // 자동완성
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
