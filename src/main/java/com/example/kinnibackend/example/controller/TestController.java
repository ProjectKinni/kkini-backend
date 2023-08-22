package com.example.kinnibackend.example.controller;

import com.example.kinnibackend.example.dto.CreateTestRequestDTO;
import com.example.kinnibackend.example.dto.TestResponseDTO;
import com.example.kinnibackend.example.service.TestService;
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
