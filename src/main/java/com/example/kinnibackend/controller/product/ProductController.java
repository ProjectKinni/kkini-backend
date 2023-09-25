package com.example.kinnibackend.controller.product;

import com.example.kinnibackend.dto.product.*;
import com.example.kinnibackend.service.product.ProductFilterService;
import com.example.kinnibackend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    //필터링, 페이징 적용 된 끼니랭킹
    @GetMapping("kkini-ranking")
    public ResponseEntity<Page<ProductResponseWithReviewCountDTO>> fetchKkiniRankingByFilters(
            ProductFilterResponseDTO filterDto,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "15") int size){
        return ResponseEntity.ok(productService.findAllKkiniRankingByCategoriesAndFilters(filterDto, page, size));
    }

    @GetMapping("/kkini-pick-products")
    public List<ProductCardListResponseDTO> getFilteredLikedProducts(
            @RequestParam Long userId,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) ProductFilteringResponseDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        return productFilterService.getFilteredLikedProducts(userId, categoryName, filterDTO, page, size);
    }

    @GetMapping("/kkini-pick-products/top")
    public List<ProductCardListResponseDTO> getTop12FilteredLikedProducts(
            @RequestParam Long userId,
            @RequestParam(required = false) String categoryName,
            ProductFilteringResponseDTO filterDTO) {
        List<ProductCardListResponseDTO> allProducts =
                productFilterService.getFilteredLikedProducts(userId, categoryName, filterDTO, 0, 12);
        return allProducts.stream().limit(12).collect(Collectors.toList());  // 상위 12개만 추출
    }

    //필터링, 페이징 적용 된 끼니그린랭킹
    @GetMapping("kkini-green")
    public ResponseEntity<Page<ProductResponseWithReviewCountDTO>> fetchKkiniGreenRankingProducts(
            ProductFilterResponseDTO filterDto,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "15") int size){
        return ResponseEntity.ok(productService.findAllGreenRanking(filterDto, page, size));
    }

    //상위 12 끼니랭킹
    @GetMapping("kkini-ranking/top")
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchTopKkiniRanking(){
        return ResponseEntity.ok(productService.findTopKkiniRanking());
    }

    //상위 12 그린랭킹
    @GetMapping("kkini-green/top")
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchTopGreenRanking(){
        return ResponseEntity.ok(productService.findTopKkiniGreen());
    }



}
