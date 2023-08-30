package com.example.kinnibackend.controller.category;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @GetMapping("/kkini")
    public List<ProductCardListResponseDTO> kkiniGreenChecked
            (@RequestParam Boolean showKkiniGreenOnly,
             @RequestParam(required = false) String searchTerm){
        return categoryService.getKkiniGreenProducts(showKkiniGreenOnly, searchTerm);
    }

    @GetMapping("/categories")
    public List<ProductCardListResponseDTO> getProductsByCategory(
            @RequestParam String categoryName,
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "false") Boolean showKkiniGreenOnly) {

        // 끼니 그린 체크리스트가 활성화되어 있으면 해당 메서드를 호출
        if (showKkiniGreenOnly) {
            return categoryService.getKkiniGreenProducts(showKkiniGreenOnly, searchTerm, categoryName);
        }

        // 끼니 그린 체크리스트가 활성화되어 있지 않으면 기존 메서드를 호출
        return categoryService.checkProductsByCategoryAndSearchTerm(categoryName, searchTerm);
    }

}

