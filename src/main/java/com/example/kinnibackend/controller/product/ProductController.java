package com.example.kinnibackend.controller.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.service.product.KkiniPickService;
import com.example.kinnibackend.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    private KkiniPickService kkiniPickService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductPreviewResponseDTO>> fetchProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/kkini-pick-products")
    public List<ProductCardListResponseDTO> getFilteredLikedProducts(
            @RequestParam Long userId,
            @RequestParam(required = false) String categoryName,
            ProductFilteringResponseDTO filterDTO) {
        return kkiniPickService.getFilteredLikedProducts(userId, categoryName, filterDTO);
    }

}
