package com.example.kinnibackend.product;

import com.example.kinnibackend.config.WebOAuthSecurityConfig;
import com.example.kinnibackend.config.oauth.OAuth2UserCustomService;
import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.service.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void getAllProductTest() {
        List<ProductPreviewResponseDTO> productList = productService.findAll();
        assertThat(productList.size()).isEqualTo(2);
        assertThat(productList.get(0).getProductName()).isEqualTo("스위트 쵸코 마카롱");
    }
}