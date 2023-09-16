package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Autowired
    private final ProductRepository productRepository;

    // 검색 기능
    public List<ProductCardListResponseDTO> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new InvalidSearchTermException("검색어가 유효하지 않습니다.");
        }

        searchTerm = searchTerm.replaceAll("\\s+", "");

        List<Product> productsByName = productRepository.findByProductName(searchTerm);
        List<Product> productsByCategory = productRepository.findByCategoryName(searchTerm);

        List<ProductCardListResponseDTO> productList = new ArrayList<>();

        for (Product product : productsByName) {
            productList.add(ProductCardListResponseDTO.fromEntity(product));
        }

        if (!productList.isEmpty()) {
            return productList;
        }

        for (Product product : productsByCategory) {
            productList.add(ProductCardListResponseDTO.fromEntity(product));
        }

        if (!productList.isEmpty()) {
            return productList;
        }
        throw new ProductNotFoundException("상품을 찾을 수 없습니다.");
    }

    // 자동 완성 기능
    public List<String> autoCompleteNames(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            throw new InvalidSearchTermException("검색어를 입력해주세요.");
        }

        String modifiedName = searchTerm.replace(" ", "%"); // 공백을 %로 대체

        // Product names
        List<String> productNames = new ArrayList<>();
        for (Product product : productRepository.findByProductName(modifiedName)) {
            productNames.add(product.getProductName());
        }

        // Category names
        List<String> categoryNames = new ArrayList<>();
        for (Product product : productRepository.findByCategoryName(modifiedName)) {
            categoryNames.add(product.getCategoryName());
        }

        // Combine product and category names
        List<String> combinedNames = new ArrayList<>();
        combinedNames.addAll(productNames);
        combinedNames.addAll(categoryNames);

        // Remove duplicates
        List<String> distinctNames = new ArrayList<>();
        for (String name : combinedNames) {
            if (!distinctNames.contains(name)) {
                distinctNames.add(name);
            }
        }
        return distinctNames;
    }

    // 상품 리스트 -> 상품 상세
    public ProductCardListResponseDTO getProductById(Long itemId) {
        Product product = productRepository.findById(itemId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ProductCardListResponseDTO.fromEntity(product);
    }
}