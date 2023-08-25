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

    @GetMapping("/products")
    public ResponseEntity<List<ProductCardListResponseDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductCardListResponseDTO> products = searchService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductCardListResponseDTO> getProductById(@PathVariable Long productId) {
        ProductCardListResponseDTO product = searchService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autoCompleteNames(@RequestParam String name) {
        List<String> names = searchService.autoCompleteNames(name);
        return ResponseEntity.ok(names);
    }
}