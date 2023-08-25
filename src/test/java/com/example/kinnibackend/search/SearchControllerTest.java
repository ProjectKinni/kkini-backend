package com.example.kinnibackend.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testSearchProductsByName() throws Exception {
        // given
        String name = "통밀식빵";

        // when & then
        mockMvc.perform(get("/search/products")
                        .param("name", name))
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
    public void testAutoCompleteNames() throws Exception {
        // given
        String name = "훈제";

        // when & then
        mockMvc.perform(get("/search/autocomplete")
                        .param("name", name))
                .andExpect(status().isOk());
    }
}
