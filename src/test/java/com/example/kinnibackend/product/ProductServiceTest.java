package com.example.kinnibackend.product;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.dto.product.ProductMainCardResponseDTO;
import com.example.kinnibackend.repository.product.ProductJPARepository;
import com.example.kinnibackend.service.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Test
    @DisplayName("SearchProductsByName")
    public void SearchProductsByNameTest(){
        String searchName = "통밀";
        Long categoryId = null;

        List<ProductCardListResponseDTO> result = productService.searchProductsByName(searchName, categoryId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("통밀식빵", result.get(0).getProductName());
    }

    @Test
    @DisplayName("getProductById")
    public void getProductByIdTest(){
        Long productId = 2L;

        ProductMainCardResponseDTO results = productService.getProductById(productId);

        assertNotNull(results);
        assertEquals("생크림카스테라", results.getProductName());
    }

}
