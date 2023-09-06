package com.example.kinnibackend.product;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void findByProductName() {
        // Given
        Product product1 = Product.builder()
                .productName("Test Product1")
                .categoryName("Test Category")
                .build();
        Product product2 = Product.builder()
                .productName("Test Product2")
                .categoryName("Test Category")
                .build();
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> foundProducts = productRepository.findByProductName("Test");

        // Then
        assertThat(foundProducts).isNotEmpty();
        assertThat(foundProducts).hasSize(2);
    }

    @Test
    @Transactional
    public void findByCategoryName() {
        // Given
        Product product1 = Product.builder()
                .productName("Product1")
                .categoryName("Test Category1")
                .build();
        Product product2 = Product.builder()
                .productName("Product2")
                .categoryName("Test Category2")
                .build();
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        List<Product> foundProducts = productRepository.findByCategoryName("Test");

        // Then
        assertThat(foundProducts).isNotEmpty();
        assertThat(foundProducts).hasSize(2);
    }

    @Test
    @Transactional
    public void findByProductId() {
        // Given
        Product product = Product.builder()
                .productName("Product1")
                .categoryName("Category1")
                .build();
        productRepository.save(product);

        // When
        Product foundProduct = productRepository.findByProductId(product.getProductId());

        // Then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getProductName()).isEqualTo(product.getProductName());
    }
}
