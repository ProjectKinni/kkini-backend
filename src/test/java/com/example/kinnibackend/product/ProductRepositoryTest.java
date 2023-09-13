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
        // Given: 더미 데이터 생성 및 저장
        Product product1 = Product.builder()
                .productName("Test Product1")
                .categoryName("Test Category1")
                .build();

        Product product2 = Product.builder()
                .productName("Test Product2")
                .categoryName("Test Category2")
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        // When: 더미 데이터를 사용하여 메서드 테스트
        List<Product> foundProducts = productRepository.findByProductName("Test");

        // Then: 결과 검증
        assertThat(foundProducts).isNotEmpty();
        assertThat(foundProducts).hasSize(2);
        assertThat(foundProducts).contains(product1, product2);
    }

    @Test
    @Transactional
    public void findByCategoryName() {

        // Given: 더미 데이터 생성 및 저장
        Product product1 = Product.builder()
                .productName("Test Product1")
                .categoryName("Test Category1")
                .build();

        Product product2 = Product.builder()
                .productName("Test Product2")
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

    @Test
    @Transactional
    public void filterProductsTest() {
        // Given: 더미 데이터 생성
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

        // When: 필터 적용하여 제품 검색
        List<Product> result = productRepository.filterProducts(
                true, null, "Test Category1", null,
                null, null, null, null, null, null,
                null, null, null, null
        );

        // Then: 결과 검증
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo("Test Category1");
        assertThat(result.get(0).getIsGreen()).isEqualTo(true);
    }
}
