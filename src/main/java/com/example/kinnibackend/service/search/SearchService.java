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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<ProductCardListResponseDTO> productList;

        // 이름으로 검색
        productList = productRepository.findByProductName(searchTerm)
                .stream()
                .map(ProductCardListResponseDTO::toProduct)
                .collect(Collectors.toList());

        if (!productList.isEmpty()) {
            return productList;
        }

        // 카테고리로 검색
        productList = productRepository.findByCategoryName(searchTerm)
                .stream()
                .map(ProductCardListResponseDTO::toProduct)
                .collect(Collectors.toList());

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

        List<String> productNames = productRepository.findByProductName(modifiedName)
                .stream()
                .map(Product::getProductName)
                .collect(Collectors.toList());

        if (productNames.isEmpty()) {
            throw new ProductNotFoundException("상품명을 찾을 수 없습니다.");
        }

        List<String> categoryNames = productRepository.findByCategoryName(modifiedName)
                .stream()
                .map(Product::getCategoryName)
                .collect(Collectors.toList());

        if (categoryNames.isEmpty()) {
            throw new CategoryNotFoundException("카테고리를 찾을 수 없습니다.");
        }

        List<String> combinedNames = Stream.concat(productNames.stream(), categoryNames.stream())
                .distinct()
                .collect(Collectors.toList());
        // 중복항목을 제거하는 이유는 사용자에게 중복된 자동 완성 항목을 보여주지 않게 하기 위함
        return combinedNames;
        // 제품 이름뿐이 아닌, 카테고리 이름도 검색 대상이기 때문에, 두 결과를 결합하여 사용자에게 더 다양한 검색결과 제공

    }

    // 상품 리스트 -> 상품 상세
    public ProductCardListResponseDTO getProductById(Long itemId) {
        return productRepository.findById(itemId)
                .map(ProductCardListResponseDTO::toProduct)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
    }
}