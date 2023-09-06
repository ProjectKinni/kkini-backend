package com.example.kinnibackend.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.service.search.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ProductRepository productRepository;

    private ProductCardListResponseDTO testProduct;

    @BeforeEach
    public void setUp() {
        Product product = Product.builder()
                .productName("Test Product")
                .categoryName("Test Category")
                .isGreen(true)
                .detail("ddd")
                .averageRating(4.52)
                .makerName("슥")
                .servingSize(100)
                .kcal(200)
                .carbohydrate(10)
                .protein(20)
                .fat(40)
                .sodium(5)
                .cholesterol(20)
                .saturatedFat(10)
                .transFat(5)
                .sugar(2)
                .score(54)
                .image("~~~~")
                .nutImage("~~~~~~~~")
                .nutScore(20)
                .build();

        Product savedProduct = productRepository.save(product);

        testProduct = ProductCardListResponseDTO.fromEntity(savedProduct);
    }


    @Test
    public void searchProducts_ValidSearchTerm_ShouldReturnProductList() {
        // Given
        String searchTerm = "Test";

        // When
        List<ProductCardListResponseDTO> result = searchService.searchProducts(searchTerm);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getProductName()).isEqualTo(testProduct.getProductName());
    }

    @Test
    public void searchProducts_InvalidSearchTerm_ShouldThrowException() {
        // Given
        String searchTerm = "";

        // When & Then
        assertThatThrownBy(() -> searchService.searchProducts(searchTerm))
                .isInstanceOf(InvalidSearchTermException.class)
                .hasMessageContaining("검색어가 유효하지 않습니다.");
    }

    @Test
    public void searchProducts_ProductNotFound_ShouldThrowException() {
        // Given
        String searchTerm = "NonExisting";

        // When & Then
        assertThatThrownBy(() -> searchService.searchProducts(searchTerm))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다.");
    }

    @Test
    public void autoCompleteNames_ValidSearchTerm_ShouldReturnNameList() {
        // Given
        String searchTerm = "Test";

        // When
        List<String> result = searchService.autoCompleteNames(searchTerm);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).contains(testProduct.getProductName(), testProduct.getCategoryName());
    }

    @Test
    public void autoCompleteNames_InvalidSearchTerm_ShouldThrowException() {
        // Given
        String searchTerm = "";

        // When & Then
        assertThatThrownBy(() -> searchService.autoCompleteNames(searchTerm))
                .isInstanceOf(InvalidSearchTermException.class)
                .hasMessageContaining("검색어를 입력해주세요.");
    }

    @Test
    public void getProductById_ValidId_ShouldReturnProduct() {
        // Given
        Long productId = testProduct.getProductId();

        // When
        ProductCardListResponseDTO result = searchService.getProductById(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo(testProduct.getProductName());
    }

    @Test
    public void getProductById_InvalidId_ShouldThrowException() {
        // Given
        Long productId = -1L;

        // When & Then
        assertThatThrownBy(() -> searchService.getProductById(productId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다.");
    }
}


