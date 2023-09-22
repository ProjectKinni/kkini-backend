package com.example.kinnibackend.controller.product;

import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //전체 상품 얻어오기
    @GetMapping
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    //Kkini Ranking 로직에 따라 전체 상품 얻어오기.
    @GetMapping("/ranking")
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchKkiniRankingProducts(){
        return ResponseEntity.ok(productService.findAllKkiniRanking());
    }




}
