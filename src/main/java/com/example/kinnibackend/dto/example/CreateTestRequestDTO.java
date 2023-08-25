package com.example.kinnibackend.dto.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTestRequestDTO {
    private String testTitle;
    private String testContent;
}
