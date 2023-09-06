package com.example.kinnibackend.category;

import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.service.productFiltering.ProductFilteringService;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductFilteringService productFilteringService;

//    @Test
//    @Transactional
//    void getKkiniProductsTest_WhenIsGreenIsTrue() {

//        boolean isGreen = true;
//
//        List<ProductCardListResponseDTO> foundProducts = categoryService.getKkiniProducts(isGreen);
//
//        assertThat(foundProducts).isNotEmpty();
//        assertTrue(foundProducts.stream().allMatch(dto ->
//                !productRepository.findById(dto.getProductId()).get().getIsKkini()));
//    }
//
//    @Test
//    @Transactional
//    void getKkiniProductsTest_WhenIsGreenIsFalse() {
//
//        boolean isGreen = false;
//
//        List<ProductCardListResponseDTO> foundProducts =
//                categoryService.getKkiniProducts(isGreen);
//
//        assertThat(foundProducts).isNotEmpty();
//    }

//    @Test
//    @Transactional
//    void checkProductsByCategoryAndSearchTerm() {
//
//        String categoryName = "간식";
//        String searchTerm = "통밀식빵";
//
//        List<ProductCardListResponseDTO> foundProducts =
//                categoryService.checkProductsByCategoryAndSearchTerm(categoryName, searchTerm);
//
//        assertThat(foundProducts).isNotEmpty();
//        assertThat(foundProducts.get(0).getProductName()).contains(searchTerm);
//        assertThat(foundProducts.get(0).getCategoryName()).isEqualTo(categoryName);
//    }
}
