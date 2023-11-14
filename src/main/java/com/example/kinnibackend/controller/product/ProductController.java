package com.example.kinnibackend.controller.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.dto.product.ProductResponseWithReviewCountDTO;
import com.example.kinnibackend.service.product.ProductFilterService;
import com.example.kinnibackend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/kkini-pick-products/top")
    public List<ProductCardListResponseDTO> getTop12FilteredLikedProducts(
            @RequestParam Long userId,
            @RequestParam(required = false) String categoryName,
            ProductFilteringResponseDTO filterDTO) {
        List<ProductCardListResponseDTO> allProducts =
                productFilterService.findTopKkiniPickRanking(userId, 0);
        System.out.println(allProducts.size());
        return allProducts.stream().limit(12).collect(Collectors.toList());  // 상위 12개만 추출
    }

    @GetMapping("/kkini-pick")
    public ResponseEntity<List<ProductCardListResponseDTO>> getFilteredKkiniPick(
            @RequestParam Long userId,
            @RequestParam int page) {

        List<ProductCardListResponseDTO> products =
                productFilterService.findTopKkiniPickRanking(userId, page);

        return ResponseEntity.ok(products);
    }

    //상위 12 끼니랭킹
    @GetMapping("kkini-ranking/top")
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchTopKkiniRanking(){
        return ResponseEntity.ok(productService.findTopKkiniRanking());
    }
    //필터링, 페이징 적용 된 끼니랭킹
    @GetMapping("kkini-ranking")
    public ResponseEntity<List<ProductResponseWithReviewCountDTO>> fetchKkiniRankingByFilters(
            @RequestParam int page){
        return ResponseEntity.ok(productService.findAllKkiniRankingByCategoriesAndFilters(page));
    }
    //상위 12 그린랭킹
    @GetMapping("kkini-green/top")
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchTopGreenRanking(){
        return ResponseEntity.ok(productService.findTopKkiniGreen());
    }
    @GetMapping("kkini-green")
    public ResponseEntity<List<ProductResponseWithReviewCountDTO>> fetchKkiniGreenRankingProducts(@RequestParam int page){
        return ResponseEntity.ok(productService.findAllGreenRanking(page));
    }
}
