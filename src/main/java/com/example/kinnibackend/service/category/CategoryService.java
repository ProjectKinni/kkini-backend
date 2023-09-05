package com.example.kinnibackend.service.category;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private final ProductRepository productRepository;

    // 끼니 그린 체크
    public List<ProductCardListResponseDTO> getKkiniGreenProducts(boolean showKkiniGreenOnly, String searchTerm) {
        List<Product> products = new ArrayList<>();

        if (showKkiniGreenOnly) {
            products.addAll(productRepository.findKkiniGreenByProductName(searchTerm));
            products.addAll(productRepository.findKkiniGreenByCategoryName(searchTerm));
        } else {
            products.addAll(productRepository.findByProductName(searchTerm));
            products.addAll(productRepository.findByCategoryName(searchTerm));
        }

        // 중복 제거
        List<Product> distinctProducts = new ArrayList<>();
        for (Product product : products) {
            if (!distinctProducts.contains(product)) {
                distinctProducts.add(product);
            }
        }

        List<ProductCardListResponseDTO> responseDTOs = new ArrayList<>();
        for (Product product : distinctProducts) {
            responseDTOs.add(ProductCardListResponseDTO.fromEntity(product));
        }
        return responseDTOs;
    }

    // 상품 리스트에서 카테고리 체크시, 해당 카테고리 리스트 상품 반환
    public List<ProductCardListResponseDTO> checkProductsByCategoryAndSearchTerm
            (String categoryName, String searchTerm) {
        List<Product> products = productRepository.findByCategoryNameAndProductNameContains(categoryName, searchTerm);

        List<ProductCardListResponseDTO> responseDTOs = new ArrayList<>();
        for (Product product : products) {
            responseDTOs.add(ProductCardListResponseDTO.fromEntity(product));
        }

        return responseDTOs;
    }

    // 끼니 그린 체크와 카테고리 체크 동시 사용
    public List<ProductCardListResponseDTO> getKkiniGreenProducts
            (boolean showKkiniGreenOnly, String searchTerm, String categoryName) {
        List<Product> products;
        if (showKkiniGreenOnly) {
            if (categoryName == null) {
                products = productRepository.findKkiniGreenByProductName(searchTerm);
            } else {
                products = productRepository.findKkiniGreenByProductNameAndCategory(searchTerm, categoryName);
            }
        } else {
            products = productRepository.findByProductName(searchTerm);
        }

        List<ProductCardListResponseDTO> responseDTOs = new ArrayList<>();
        for (Product product : products) {
            responseDTOs.add(ProductCardListResponseDTO.fromEntity(product));
        }

        return responseDTOs;
    }
}
