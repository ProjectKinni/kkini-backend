package com.example.kinnibackend.controller.productFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.productFiltering.ProductFilteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductFilteringController {

    @Autowired
    private final ProductFilteringService productFilteringService;

    @GetMapping("/kkini")
    public List<ProductCardListResponseDTO> isGreenChecked
            (@RequestParam Boolean isGreen,
             @RequestParam(required = false) String searchTerm){
        return productFilteringService.getIsGreenProductsAndCategory(isGreen, searchTerm);
    }

    @GetMapping("/categories")
    public List<ProductCardListResponseDTO> getProductsByCategory(
            @RequestParam String categoryName,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "false") Boolean isGreen) {

        // 끼니 그린 체크리스트가 활성화되어 있으면 해당 메서드를 호출
        if (isGreen) {
            return productFilteringService.getIsGreenProductsAndCategory(isGreen, searchTerm, categoryName);
        }

        // 끼니 그린 체크리스트가 활성화되어 있지 않으면 기존 메서드를 호출
        return productFilteringService.checkProductsByCategoryAndSearchTerm(categoryName, searchTerm);
    }

}

