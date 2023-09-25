package com.example.kinnibackend.controller.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.dto.product.ProductResponseWithReviewCountDTO;
import com.example.kinnibackend.service.product.ProductFilterService;
import com.example.kinnibackend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final ProductFilterService productFilterService;

    //전체 상품 얻어오기
    @GetMapping
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    //Kkini Ranking 로직에 따라 전체 상품 얻어오기.
    @GetMapping("/kkini-ranking")
    public ResponseEntity<List<ProductResponseWithReviewCountDTO>> fetchKkiniRankingProducts(){
        return ResponseEntity.ok(productService.findAllKkiniRanking());
    }

    @GetMapping("/kkini-pick-products")
    public List<ProductCardListResponseDTO> getFilteredLikedProducts(
            @RequestParam Long userId,
            @RequestParam(required = false) String categoryName,
            ProductFilteringResponseDTO filterDTO) {
        return productFilterService.getFilteredLikedProducts(userId, categoryName, filterDTO);
    }

    //kkini-green Ranking
    @GetMapping("kkini-green")
    public ResponseEntity<List<ProductResponseWithReviewCountDTO>> fetchKkiniGreenRankingProducts(){
        return ResponseEntity.ok(productService.findAllGreenRanking());
    }


}
