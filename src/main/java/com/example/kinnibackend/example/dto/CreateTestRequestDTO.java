package com.example.kinnibackend.example.dto;

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
