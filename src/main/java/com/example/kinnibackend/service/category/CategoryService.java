package com.example.kinnibackend.service.category;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final ProductRepository productRepository;
    // 끼니 그린 추가하기

    // 상품 리스트에서 카테고리 체크시, 해당 카테고리 리스트 상품 반환
    public List<ProductCardListResponseDTO> filterProductsByCategory(String categoryName) {
        List<Product> filteredProducts = productRepository.findByCategoryName(categoryName);

        // Product 객체를 ProductCardListResponseDTO 객체로 변환
        List<ProductCardListResponseDTO> responseDTOs = new ArrayList<>();
        for (Product product : filteredProducts) {
            ProductCardListResponseDTO dto = new ProductCardListResponseDTO();
            dto.setCategoryName(product.getCategoryName()); // 카테고리 이름 설정
            // 다른 dto의 필드들도 product의 필드들로 설정
            responseDTOs.add(dto);
        }
        return responseDTOs;
    }
}
