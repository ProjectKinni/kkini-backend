package com.example.kinnibackend.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.service.search.SearchService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchServiceTest {
    @Autowired
    private SearchService searchService;

    @Test
    @Transactional
    public void searchProductsByNameTest() {
        // given
        String searchName = "통밀";

        // when
        List<ProductCardListResponseDTO> result = searchService.searchProductsByName(searchName);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(product -> product.getProductName().contains(searchName)));
    }

    @Test
    @Transactional
    public void getItemByIdTest() {
        // given
        Long productId = 1L;

        // when
        ProductCardListResponseDTO result = searchService.getProductById(productId);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
    }

    @Test
    @Transactional
    public void autoCompleteNamesTest() {
        // given
        String searchName = "훈제";

        // when
        List<String> results = searchService.autoCompleteNames(searchName);

        // then
        assertTrue(results.contains("훈제 오리"));
        assertTrue(results.contains("훈제 연어"));
        assertTrue(results.contains("훈제란"));
        assertFalse(results.contains("통밀식빵"));
    }

    @Test
    @Transactional
    public void filterLowCalorieProductsTest() {
        // When
        List<ProductCardListResponseDTO> lowCalorieProducts = searchService.filterProductsByCriteria("저칼로리");

        // Then
        assertNotNull(lowCalorieProducts);
        assertFalse(lowCalorieProducts.isEmpty());

        for(ProductCardListResponseDTO product : lowCalorieProducts){
            System.out.println(product.getProductName() + product.getCalorie());
        }
        // 모든 반환된 상품이 저칼로리 조건을 만족하는지 확인
        assertTrue(lowCalorieProducts.stream().allMatch(product -> product.getCalorie() <= product.getTotalAmount() * 2));

    }

    @Test
    @Transactional
    public void filterSugarFreeProductsTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("슈가프리");

        // Then
        assertTrue(products.stream().allMatch(product -> product.getSugar() / product.getTotalAmount() * 0.05 <= 5));
        assertTrue(products.stream().anyMatch(product -> product.getProductName().equals("생오징어")));
    }

    @Test
    @Transactional
    public void filterLowSugarProductsTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("로우슈가");

        for(ProductCardListResponseDTO product : products){
            System.out.println(product.getProductName() + product.getSugar());
        }
        // Then
        assertTrue(products.stream().allMatch(product -> product.getSugar() <= product.getTotalAmount() * 0.05));
        assertTrue(products.stream().anyMatch(product -> product.getProductName().equals("생오징어")));

    }

    @Test
    @Transactional
    public void filterByLowCarbTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("저탄수화물");

        for(ProductCardListResponseDTO product : products){
            System.out.println(product.getProductName() + product.getCarb());
        }

        // Then
        assertTrue(products.stream().allMatch(product -> product.getCarb() >= product.getTotalAmount() * 0.11
                && product.getCarb() <= product.getTotalAmount() * 0.20));
        assertTrue(products.stream().anyMatch(product -> product.getProductName().equals("다시마부각")));

    }

    @Test
    @Transactional
    public void filterByKetoTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("키토");

        // Then
        assertTrue(products.stream().allMatch(product -> product.getCarb() <= product.getTotalAmount() * 0.10));
    }

    @Test
    @Transactional
    public void filterByTransFatTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("트랜스 지방");

        // Then
        assertTrue(products.stream().allMatch(product -> product.getTransFat() <= 1));
    }

    @Test
    @Transactional
    public void filterByHighProteinTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("고단백");

        for(ProductCardListResponseDTO product : products){
            System.out.println(product.getProductName() + product.getProtein());
        }

        // Then
        assertTrue(products.stream().allMatch(product -> product.getProtein() >= product.getTotalAmount() * 0.20));
        assertTrue(products.stream().anyMatch(product -> product.getProductName().equals("통밀식빵")));
    }

    @Test
    @Transactional
    public void filterByLowSodiumTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("저나트륨");

        for(ProductCardListResponseDTO product : products){
            System.out.println(product.getProductName() + product.getSodium());
        }
        // Then
        assertTrue(products.stream().allMatch(product -> product.getSodium() <= product.getTotalAmount() * 2));
    }

    @Test
    @Transactional
    public void filterBySaturatedFatTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("포화지방");

        // Then
        assertTrue(products.stream().allMatch(product -> product.getSaturatedFat() <= product.getTotalAmount() * 0.02));
    }

    @Test
    @Transactional
    public void filterByLowFatTest() {
        // When
        List<ProductCardListResponseDTO> products = searchService.filterProductsByCriteria("저지방");

        // Then
        assertTrue(products.stream().allMatch(product -> product.getFat() <= product.getTotalAmount() * 0.04));
    }
}
