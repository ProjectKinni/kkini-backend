package com.example.kinnibackend.searchAndFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductFilteringResponseDTO;
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
    private ProductCardListResponseDTO testProduct;

    @BeforeEach
    public void setUp() {
        // Given: 더미 데이터 생성
        testProduct = ProductCardListResponseDTO.builder()
                .productName("Test Product")
                .categoryName("Test Category")
                .isGreen(true)
                .detail("ddd")
                .averageRating(4.52f)
                .makerName("슥")
                .servingSize(100d)
                .kcal(200d)
                .carbohydrate(10d)
                .protein(20d)
                .fat(40d)
                .sodium(5d)
                .cholesterol(20d)
                .saturatedFat(10d)
                .transFat(5d)
                .sugar(2d)
                .score(54d)
                .image("~~~~")
                .nutImage("~~~~")
                .nutScore(30d)
                .build();

        Product product = Product.builder()
                .productName(testProduct.getProductName())
                .categoryName(testProduct.getCategoryName())
                .isGreen(testProduct.getIsGreen())
                .detail(testProduct.getDetail())
                .averageRating(testProduct.getAverageRating())
                .makerName(testProduct.getMakerName())
                .servingSize(testProduct.getServingSize())
                .kcal(testProduct.getKcal())
                .carbohydrate(testProduct.getCarbohydrate())
                .protein(testProduct.getProtein())
                .fat(testProduct.getFat())
                .sodium(testProduct.getSodium())
                .cholesterol(testProduct.getCholesterol())
                .saturatedFat(testProduct.getSaturatedFat())
                .transFat(testProduct.getTransFat())
                .sugar(testProduct.getSugar())
                .score(testProduct.getScore())
                .image(testProduct.getImage())
                .nutImage(testProduct.getNutImage())
                .nutScore(testProduct.getNutScore())
                .build();

        Product savedProduct = productRepository.save(product);
        testProduct.setProductId(savedProduct.getProductId());
    }
    @Test
    @Transactional
    public void filterProducts_isGreenFilter(){
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsGreen(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getIsGreen()).isEqualTo(testProduct.getIsGreen());
    }

    @Test
    @Transactional
    public void filterProducts_CategoryFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setCategoryName("Test Category");

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo(filterDTO.getCategoryName());
    }
    @Test
    @Transactional
    public void filterProducts_LowCalorieFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsLowCalorie(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getKcal() < 40 ||
                (product.getCategoryName().equals("음료") && product.getKcal() < 20));
    }

    @Test
    @Transactional
    public void filterProducts_SugarFreeFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsSugarFree(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getSugar() <= 1);
    }

    @Test
    @Transactional
    public void filterProducts_LowCarbFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsLowCarb(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product ->
                product.getCarbohydrate() != null && product.getServingSize() != null &&
                        product.getCarbohydrate() >= product.getServingSize() * 0.11 &&
                        product.getCarbohydrate() <= product.getServingSize() * 0.20
        );
    }

    @Test
    @Transactional
    public void filterProducts_HighProteinFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsHighProtein(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getProtein() >= product.getServingSize() * 0.20);
    }

    @Test
    @Transactional
    public void filterProducts_LowSodiumFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsLowSodium(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getSodium() <= product.getServingSize() * 2);
    }

    @Test
    @Transactional
    public void filterProducts_LowCholesterolFilter() {
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsCholesterol(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getCholesterol() < 300);
    }

    @Test
    @Transactional
    public void filterProducts_filters(){
        // Given
        ProductFilteringResponseDTO filterDTO = new ProductFilteringResponseDTO();
        filterDTO.setIsLowCalorie(true);
        filterDTO.setIsSugarFree(true);
        filterDTO.setIsLowSugar(true);

        // When
        List<ProductCardListResponseDTO> result = productFilteringService.filterProducts(filterDTO);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(product -> product.getKcal() < 40);
        assertThat(result).allMatch(product -> product.getSugar() <= 1);
        assertThat(result).allMatch(product -> product.getSugar() >= 0 && product.getSugar() < 5);
    }

}
