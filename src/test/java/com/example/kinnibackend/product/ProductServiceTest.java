package com.example.kinnibackend.product;

import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void updateScoreTest(){
        Long productId = 50L;

        productService.updateProductScore(productId);

        System.out.println(productRepository.findByProductId(productId).getScore());
        assertThat(productRepository.findByProductId(productId).getScore()).isEqualTo(0);

    }
}
