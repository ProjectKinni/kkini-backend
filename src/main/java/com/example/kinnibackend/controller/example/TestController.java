package com.example.kinnibackend.controller.example;

import com.example.kinnibackend.dto.example.CreateTestRequestDTO;
import com.example.kinnibackend.dto.example.TestResponseDTO;
import com.example.kinnibackend.service.example.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

//    PostMapping = Create
    @PostMapping("/tests")
    public TestResponseDTO createTest(@RequestBody CreateTestRequestDTO createTestRequestDTO) {
        return testService.createTest(createTestRequestDTO);
    }

//    GetMapping = Read
    @GetMapping("/tests/{testId}")
    public TestResponseDTO getTest(@PathVariable Long testId) {
        return testService.getTest(testId);
    }
}
