package com.example.kinnibackend.service.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductJPARepository productJPARepository;

    // 상품 검색시, 카테고리 / 이름으로 검색
    public List<ProductCardListResponseDTO> searchProductsByNameAndCategory(String productName, String categoryName) {
        List<ProductCardListResponseDTO> productList;

        // 띄어쓰기 제거
        productName = (productName != null) ? productName.replaceAll("\\s+", "") : null;
        categoryName = (categoryName != null) ? categoryName.replaceAll("\\s+", "") : null;

        productList = searchByCriteria(categoryName, productJPARepository::findByCategoryNameWithoutSpaces);
        if (!productList.isEmpty()) {
            return productList;
        }

        productList = searchByCriteria(productName, productJPARepository::findByProductNameWithoutSpaces);
        if (!productList.isEmpty()) {
            return productList;
        }

        productList = searchByCriteria(productName, productJPARepository::findByCategoryNameWithoutSpaces);
        return productList;
    }

    private List<ProductCardListResponseDTO> searchByCriteria(String criteria, Function<String, List<Product>> searchFunction) {
        if (criteria != null && !criteria.trim().isEmpty()) {
            return searchFunction.apply(criteria)
                    .stream()
                    .map(Product::toProductCardListResponseDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // 자동완성기능(2글자 이상 일치하면 해당 상품명 혹은 카테고리명으로 자동 검색)
    public List<String> autoCompleteNames(String name) {
        if (name == null || name.length() < 2) {
            return Collections.emptyList();
        }

        String modifiedName = name.replace(" ", "%");

        // 상품명으로 검색
        List<String> productNames = productJPARepository
                .findByProductNameWithoutSpaces(modifiedName)
                .stream()
                .map(Product::getProductName)
                .collect(Collectors.toList());

        // 카테고리명으로 검색 (이 부분은 Product 엔터티와 연관된 카테고리 엔터티나 메서드에 따라 다를 수 있습니다.)
        List<String> categoryNames = productJPARepository
                .findByCategoryNameWithoutSpaces(modifiedName)
                .stream()
                .map(Product::getCategoryName) // Product 엔터티에 getCategoryName 메서드가 있다고 가정
                .collect(Collectors.toList());

        // 상품명과 카테고리명을 합친 후 중복 제거
        List<String> combinedNames = Stream.concat(productNames.stream(), categoryNames.stream())
                .distinct()
                .collect(Collectors.toList());

        return combinedNames;
    }

    /// 상품의 상세정보 조회시, 사용(상품리스트->상품 상세)
    public ProductCardListResponseDTO getProductById(Long itemId) {
        return productJPARepository.findById(itemId)
                .map(Product::toProductCardListResponseDTO)
                .orElse(null);
    }

    // 끼니인지, 끼니 그린인지 체크
    public List<ProductCardListResponseDTO> getProductsByKkiniType(boolean isKkini) {
        List<Product> products;

        if (isKkini) {
            products = productJPARepository.findByIsKkini(true);
        } else {
            products = productJPARepository.findByIsKkini(false);
        }

        return products.stream()
                .map(Product::toProductCardListResponseDTO)
                .collect(Collectors.toList());
    }

    // 상품 리스트에서 카테고리 체크시, 해당 카테고리 리스트 상품 반환
    public List<ProductCardListResponseDTO> filterProductsByCategory(String categoryName) {
        List<Product> filteredProducts = productJPARepository.findByCategoryNameWithoutSpaces(categoryName);

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