package com.example.kinnibackend.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class TestResponseDTO {
    private Long testId;
    private String testTitle;
    private String testContent;
}