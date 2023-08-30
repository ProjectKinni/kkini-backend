package com.example.kinnibackend.search;

import com.example.kinnibackend.repository.product.ProductRepository;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testSearchProductsByName() throws Exception {
        // given
        String searchTerm = "통밀식빵";

        // when & then
        mockMvc.perform(get("/search/products")
                        .param("searchTerm", searchTerm))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchCategoryByName() throws Exception {
        // given
        String searchTerm = "간식";

        // when & then
        mockMvc.perform(get("/search/products")
                        .param("searchTerm", searchTerm))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetProductById() throws Exception {
        // given
        Long itemId = 2L;

        // when & then
        mockMvc.perform(get("/search/products/" + itemId))
                .andExpect(status().isOk());
    }
    @Test
    public void testAutoCompleteProductNames() throws Exception {
        // given
        String name = "훈제";

        // when & then
        mockMvc.perform(get("/search/autocomplete")
                        .param("name", name))
                .andExpect(status().isOk());
    }
    @Test
    public void testAutoCompleteCategoryNames() throws Exception {
        // given
        String name = "육가";

        // when & then
        mockMvc.perform(get("/search/autocomplete")
                        .param("name", name))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetProductsByKkiniTypeForKkini() throws Exception {
        // given
        boolean type = true;

        // when & then
        mockMvc.perform(get("/search/kkini-products/" + type))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetProductsByKkiniTypeForKkiniGreen() throws Exception {
        // given
        boolean type = false;

        // when & then
        mockMvc.perform(get("/search/kkini-products/" + type))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchProductsByCategory() throws Exception {
        // given
        String categoryName = "음료";

        // when & then
        mockMvc.perform(get("/search/products/categoryChecked")
                        .param("categoryName", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(4)));
    }
    @Test
    public void testGetFilteredProducts() throws Exception {
        // given
        String criteria = "키토";

        // when & then
        mockMvc.perform(get("/search/products/filtered-products")
                        .param("criteria", criteria))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(9)));
    }
}
