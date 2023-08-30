package com.example.kinnibackend.search;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.service.search.SearchService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    @Transactional
    public void searchProductsByCategoryName() {
        // given
        String categoryName = "간식";

        // when
        List<ProductCardListResponseDTO> results = searchService.searchProducts(categoryName);

        System.out.println(results);
        // then
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(product -> "간식".equals(product.getCategoryName())));
    }

    @Test
    @Transactional
    public void searchProductsByProductName() {
        // given
        String productName = "훈제";

        // when
        List<ProductCardListResponseDTO> results = searchService.searchProducts(productName);

        // then
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(product -> product.getProductName().contains("훈제")));
    }

    @Test
    @Transactional
    public void getItemById() {
        // given
        Long productId = 1L;

        // when
        ProductCardListResponseDTO result = searchService.getProductById(productId);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
    }

    // 예외 처리 테스트
    @Test
    public void searchProducts_InvalidSearchTerm_ThrowsException() {
        assertThrows(InvalidSearchTermException.class, () -> {
            searchService.searchProducts(null);
        });
        assertThrows(InvalidSearchTermException.class, () -> {
            searchService.searchProducts("");
        });
    }

    @Test
    public void autoCompleteNames_TooShortName_ThrowsException() {
        assertThrows(InvalidSearchTermException.class, () -> {
            searchService.autoCompleteNames("훈");
        });
    }

    // 통합 테스트
    @Test
    public void searchService_IntegrationTest() {
        String searchTerm = "통밀식빵";
        List<ProductCardListResponseDTO> results = searchService.searchProducts(searchTerm);
        assertFalse(results.isEmpty());
        assertEquals(searchTerm, results.get(0).getProductName());
    }
}
