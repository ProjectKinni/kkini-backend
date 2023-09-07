package com.example.kinnibackend.searchAndFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.service.productFiltering.ProductFilteringService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductFilteringServiceTest {
    @Autowired
    private ProductFilteringService productFilteringService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        Product product1 = Product.builder()
                .productName("Test Product1")
                .categoryName("Test Category1")
                .isGreen(true)
                .kcal(30.0)
                .sugar(1.0)
                .carbohydrate(15.0)
                .fat(2.0)
                .transFat(0.5)
                .saturatedFat(0.5)
                .protein(10.0)
                .sodium(1.5)
                .cholesterol(200.0)
                .servingSize(100.0)
                .build();

        Product product2 = Product.builder()
                .productName("Test Product2")
                .categoryName("Test Category2")
                .isGreen(false)
                .kcal(50.0)
                .sugar(5.0)
                .carbohydrate(20.0)
                .fat(5.0)
                .transFat(1.0)
                .saturatedFat(1.0)
                .protein(21.0)
                .sodium(2.0)
                .cholesterol(300.0)
                .servingSize(100.0)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    @Transactional
    public void filterProducts_CategoryFilter() {
        // Given
        String categoryName = "Test Category1";

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, categoryName, null, null, null,
                null, null, null, null, null, null,
                null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo(categoryName);
    }

    @Test
    @Transactional
    public void filterProducts_LowCalorieFilter() {
        // Given
        Boolean isLowCalorie = true;

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, null, isLowCalorie, null, null,
                null, null, null, null, null, null,
                null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getKcal() < 40 ||
                (product.getCategoryName().equals("음료") && product.getKcal() < 20));
    }

    @Test
    @Transactional
    public void filterProducts_SugarFreeFilter() {
        // Given
        Boolean isSugarFree = true;

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, null, null, isSugarFree, null,
                null, null, null, null, null, null,
                null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getSugar() <= 1);
    }

    @Test
    @Transactional
    public void filterProducts_LowCarbFilter() {
        // Given
        Boolean isLowCarb = true;

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, null, null, null, isLowCarb,
                null, null, null, null, null, null,
                null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getCarbohydrate() >= product.getServingSize() * 0.11 &&
                product.getCarbohydrate() <= product.getServingSize() * 0.20);
    }

    @Test
    @Transactional
    public void filterProducts_HighProteinFilter() {
        // Given
        Boolean isHighProtein = true;

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, null, null, null,
                null, null, null, null, isHighProtein, null,
                null, null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getProtein() >= product.getServingSize() * 0.20);
    }

    @Test
    @Transactional
    public void filterProducts_LowSodiumFilter() {
        // Given
        Boolean isLowSodium = true;

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, null, null, null, null,
                null, null, null, null, isLowSodium, null,
                null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getSodium() <= product.getServingSize() * 2);
    }

    @Test
    @Transactional
    public void filterProducts_LowCholesterolFilter() {
        // Given
        Boolean isCholesterol = true;

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(
                null, null, null, null, null, null,
                null, null, null, null, null, isCholesterol,
                null, null);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getCholesterol() < 300);
    }
}
