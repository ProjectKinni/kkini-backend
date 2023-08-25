package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductJPARepository productJPARepository;

    // 상품 검색시, 카테고리 / 이름으로 검색
    public List<ProductCardListResponseDTO> searchProductsByName(String productName, String categoryName) {
        List<ProductCardListResponseDTO> productList;

        // 1. 카테고리로 검색
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            productList = productJPARepository.findByCategoryNameOrderByAverageRatingDesc(categoryName)
                    .stream()
                    .map(Product::toProductCardListResponseDTO)
                    .collect(Collectors.toList());

            if (!productList.isEmpty()) {
                return productList;
            }
        }

        // 2. 상품명으로 검색
        if (productName != null && !productName.trim().isEmpty()) {
            productList = productJPARepository.findByProductNameContainingOrderByAverageRatingDesc(productName)
                    .stream()
                    .map(Product::toProductCardListResponseDTO)
                    .collect(Collectors.toList());

            if (!productList.isEmpty()) {
                return productList;
            }

            // 2.1. 상품명으로 카테고리 검색
            List<Product> productsWithCategoryName = productJPARepository.findByCategoryNameContaining(productName);
            if (!productsWithCategoryName.isEmpty()) {
                productList = productsWithCategoryName.stream()
                        .map(Product::toProductCardListResponseDTO)
                        .collect(Collectors.toList());
                return productList;
            }
        }

        return Collections.emptyList();
    }

    // 자동완성기능(2글자 이상 일치하면 해당 상품명 혹은 카테고리명으로 자동 검색)
    public List<String> autoCompleteNames(String name) {
        if (name == null || name.length() < 2) {
            return Collections.emptyList();
        }

        String modifiedName = name.replace(" ", "%");

        List<String> productNames = productJPARepository
                .findByProductNameContainingOrderByAverageRatingDesc(modifiedName)
                .stream()
                .map(Product::getProductName)
                .collect(Collectors.toList());

        return productNames;
    }

    /// 상품의 상세정보 조회시, 사용(상품리스트->상품 상세)
    public ProductCardListResponseDTO getProductById(Long itemId) {
        return productJPARepository.findById(itemId)
                .map(Product::toProductCardListResponseDTO)
                .orElse(null);
    }

    // 끼니인지, 끼니 그린인지 체크
    public List<ProductCardListResponseDTO> getProductsByKkiniType(String type) {
        List<Product> products;

        if ("kkini".equals(type)) {
            products = productJPARepository.findByIsKkiniTrueOrIsKkiniFalse();
        } else if ("kkini-green".equals(type)) {
            products = productJPARepository.findByIsKkiniFalse();
        } else {
            throw new IllegalArgumentException("Invalid kkini type");
        }

        return products.stream()
                .map(Product::toProductCardListResponseDTO)
                .collect(Collectors.toList());
    }

    // 상품 리스트에서 카테고리 체크시, 해당 카테고리 리스트 상품 반환
    public List<ProductCardListResponseDTO> filterProductsByCategory(String categoryName) {
        List<Product> filteredProducts = productJPARepository.findByCategoryName(categoryName);

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

    // 상품 리스트에서 필터링 체크시, 해당 필터링으로 상품 반환
    public List<ProductCardListResponseDTO> filterProductsByCriteria(String criteria) {
        List<ProductCardListResponseDTO> products = productJPARepository.findAll()
                .stream()
                .map(Product::toProductCardListResponseDTO)
                .collect(Collectors.toList());

        switch(criteria) {
            case "저칼로리":
                products = filterByLowCalorie(products);
                break;
            case "슈가프리":
                products = filterBySugarFree(products);
                break;
            case "로우슈가":
                products = filterByLowSugar(products);
                break;
            case "저탄수화물":
                products = filterByLowCarb(products);
                break;
            case "키토":
                products = filterByKeto(products);
                break;
            case "트랜스 지방":
                products = filterByTransFat(products);
                break;
            case "고단백":
                products = filterByHighProtein(products);
                break;
            case "저나트륨":
                products = filterByLowSodium(products);
                break;
            case "포화지방":
                products = filterBySaturatedFat(products);
                break;
            case "저지방":
                products = filterByLowFat(products);
                break;
        }

        return products;
    }

    //필터들..
    private List<ProductCardListResponseDTO> filterByLowCalorie(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getCalorie() <= product.getTotalAmount() * 2)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterBySugarFree(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSugar() <= 1)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowSugar(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSugar() <= product.getTotalAmount() * 0.05) // 5%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowCarb(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getCarb() >= product.getTotalAmount() * 0.11
                        && product.getCarb() <= product.getTotalAmount() * 0.20) // 11~20%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByKeto(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getCarb() <= product.getTotalAmount() * 0.10) // 10%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByTransFat(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getTransFat() <= 1)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByHighProtein(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getProtein() >= product.getTotalAmount() * 0.20) // 20% 이상의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowSodium(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSodium() <= product.getTotalAmount() * 2)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterBySaturatedFat(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSaturatedFat() <= product.getTotalAmount() * 0.02) // 2%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowFat(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getFat() <= product.getTotalAmount() * 0.04) // 4%의 기준값
                .collect(Collectors.toList());
    }
}

