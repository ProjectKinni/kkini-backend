package com.example.kinnibackend.productAndFiltering;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchAndFilteringControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;
    private Product product;
    private Product savedProduct;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        product = Product.builder()
                .productName("Test Product")
                .categoryName("Test Category")
                .isGreen(true)
                .detail("Test Detail")
                .averageRating(4.5f)
                .makerName("Test Maker")
                .build();

        savedProduct = productRepository.save(product);
        productRepository.flush();
        System.out.println("product ID : " + savedProduct.getProductId());
    }

    @Test
    @Transactional
    public void searchAndFilterProducts() throws Exception {
        // Given
        String searchTerm = "Test";

        // When & Then
        mockMvc.perform(get("/api/products/search")
                        .param("searchTerm", searchTerm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].productName", containsString(searchTerm)));
    }

    @Test
    @Transactional
    public void autoCompleteNames() throws Exception {
        // Given
        String searchTerm = "Test";

        // When & Then
        mockMvc.perform(get("/api/products/autocomplete")
                        .param("searchTerm", searchTerm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0]", containsString(searchTerm)));
    }

    @Test
    @Transactional
    public void getProductById() throws Exception {
        // Given
        Long productId = savedProduct.getProductId();

        // When & Then
        mockMvc.perform(get("/api/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is("Test Product")));
    }
}
