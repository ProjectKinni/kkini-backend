package com.example.kinnibackend.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ProductFilteringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void KkiniGreenChecked() throws Exception{
        // given
        String searchTerm = "통밀식빵";
        Boolean isKkini = true;


        // When & Then
        mockMvc.perform(get("/category/kkini")
                        .param("searchTerm", searchTerm)
                        .param("isKkini", isKkini.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value(searchTerm))
                .andExpect(jsonPath("$[0].isKkini").value(isKkini));

    }

    @Test
    void categoryChecked() throws Exception{
        // given
        String searchTerm = "통밀식빵";
        String categoryName = "간식";

        // When & Then
        mockMvc.perform(get("/category/categories")
                        .param("categoryName", categoryName)
                        .param("searchTerm", searchTerm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value(searchTerm));
    }
}
