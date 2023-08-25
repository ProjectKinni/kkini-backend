package com.example.kinnibackend.service.example;

import com.example.kinnibackend.dto.example.CreateTestRequestDTO;
import com.example.kinnibackend.dto.example.TestResponseDTO;
import com.example.kinnibackend.entity.Test;
import com.example.kinnibackend.repository.example.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final TestRepository testRepository;

    @Autowired
    public TestService (TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public TestResponseDTO createTest(CreateTestRequestDTO createTestRequestDTO) {
//        RequestDTO -> Entity 변환
        Test test = Test.builder()
                .testTitle(createTestRequestDTO.getTestTitle())
                .testContent(createTestRequestDTO.getTestContent())
                .build();
//        MySQL DB에 저장
        testRepository.save(test);
//        ResponseDTO 반환 (testId, testTitle, testContent 만 반환)
        return new TestResponseDTO(test.getTestId(), test.getTestTitle(), test.getTestContent());
    }

    public TestResponseDTO getTest(Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        return new TestResponseDTO(test.getTestId(), test.getTestTitle(), test.getTestContent());
    }


}
