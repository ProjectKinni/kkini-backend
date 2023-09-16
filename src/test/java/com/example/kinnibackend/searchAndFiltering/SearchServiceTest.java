package com.example.kinnibackend.searchAndFiltering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.service.search.SearchService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

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
    public void searchProducts_ValidSearchTerm() {
        // Given
        String searchTerm = "Test";

        // When
        List<ProductCardListResponseDTO> result = searchService.searchProducts(searchTerm);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getProductName()).isEqualTo(testProduct.getProductName());
    }

    @Test
    @Transactional
    public void searchProducts_InvalidSearchTerm() {
        // Given
        String searchTerm = "";

        // When & Then
        assertThatThrownBy(() -> searchService.searchProducts(searchTerm))
                .isInstanceOf(InvalidSearchTermException.class)
                .hasMessageContaining("검색어가 유효하지 않습니다.");
    }

    @Test
    @Transactional
    public void searchProducts_ProductNotFound() {
        // Given
        String searchTerm = "NonExisting";

        // When & Then
        assertThatThrownBy(() -> searchService.searchProducts(searchTerm))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다.");
    }

    @Test
    @Transactional
    public void autoCompleteNames_ValidSearchTerm() {
        // Given
        String searchTerm = "Test";

        // When
        List<String> result = searchService.autoCompleteNames(searchTerm);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).contains(testProduct.getProductName(), testProduct.getCategoryName());
    }

    @Test
    @Transactional
    public void autoCompleteNames_InvalidSearchTerm() {
        // Given
        String searchTerm = "";

        // When & Then
        assertThatThrownBy(() -> searchService.autoCompleteNames(searchTerm))
                .isInstanceOf(InvalidSearchTermException.class)
                .hasMessageContaining("검색어를 입력해주세요.");
    }

    @Test
    @Transactional
    public void getProductById_ValidId() {
        // Given
        Long productId = testProduct.getProductId();

        // When
        ProductCardListResponseDTO result = searchService.getProductById(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo(testProduct.getProductName());
    }

    @Test
    @Transactional
    public void getProductById_InvalidId() {
        // Given
        Long productId = -1L;

        // When & Then
        assertThatThrownBy(() -> searchService.getProductById(productId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다.");
    }
}


