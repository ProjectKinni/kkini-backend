package com.example.kinnibackend.controller.productFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.productFiltering.ProductFilteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/filter")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductFilteringController {

    @Autowired
    private final ProductFilteringService productFilteringService;

    // 상품 필터링
    @GetMapping
    public ResponseEntity<List<ProductCardListResponseDTO>> filterProducts(
            @RequestParam(required = false) Boolean isGreen,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String criteria) {

        List<ProductCardListResponseDTO> searchResults =
                productFilteringService.searchProducts(isGreen, searchTerm, categoryName);

        if (criteria != null && !criteria.trim().isEmpty()) {
            List<ProductCardListResponseDTO> filterResults =
                    productFilteringService.filterProductsByCriteria(criteria);
            searchResults.retainAll(filterResults);
        }

        return ResponseEntity.ok(searchResults);
    }
}

