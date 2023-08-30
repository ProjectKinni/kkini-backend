package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.exception.search.CategoryNotFoundException;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<String> autoCompleteNames(String name) {
        if (name == null || name.length() < 2) {
            throw new InvalidSearchTermException("검색어가 너무 짧습니다.");
        }

        String modifiedName = name.replace(" ", "%"); // 공백을 %로 대체

        List<String> productNames = new ArrayList<>();
        for (Product product : productRepository.findByProductName(modifiedName)) {
            productNames.add(product.getProductName());
        }

        if (productNames.isEmpty()) {
            throw new ProductNotFoundException("상품명을 찾을 수 없습니다.");
        }

        List<String> categoryNames = new ArrayList<>();
        for (Product product : productRepository.findByCategoryName(modifiedName)) {
            categoryNames.add(product.getCategoryName());
        }

        if (categoryNames.isEmpty()) {
            throw new CategoryNotFoundException("카테고리를 찾을 수 없습니다.");
        }

        List<String> combinedNames = new ArrayList<>();
        combinedNames.addAll(productNames);
        combinedNames.addAll(categoryNames);

        // 제품이름과 카테고리 이름에 동일항목이 있다면, combinedNames에 두번 포함되므로
        // 중복 항복을 제거하여 사용자에게 동일한 자동완성항목을 두 번 보여주지 않기 위함
        return combinedNames.stream().distinct().collect(Collectors.toList());
    }

    // 상품 리스트 -> 상품 상세
    public ProductCardListResponseDTO getProductById(Long itemId) {
        Product product = productRepository.findById(itemId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));

        return ProductCardListResponseDTO.fromEntity(product);
    }
}