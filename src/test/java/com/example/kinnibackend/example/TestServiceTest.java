package com.example.kinnibackend.example;

import com.example.kinnibackend.example.dto.CreateTestRequestDTO;
import com.example.kinnibackend.example.dto.TestResponseDTO;
import com.example.kinnibackend.example.entity.Test;
import com.example.kinnibackend.example.repository.TestRepository;
import com.example.kinnibackend.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @MockBean
    private TestRepository testRepository;

    // entity 이름이 Test라 import를 이런식으로 해와야 같이 쓸 수 있음
    // 실제 테스트코드 작성 때는 import에 적어주고 @Test 어노테이션 사용
    @org.junit.jupiter.api.Test
    public void testCreateTest() {
        // given
        String testTitle = "Test Title";
        String testContent = "Test Content";
        CreateTestRequestDTO createTestRequestDTO = new CreateTestRequestDTO(testTitle, testContent);
        Test test = Test.builder()
                .testTitle(testTitle)
                .testContent(testContent)
                .build();
        when(testRepository.save(any(Test.class))).thenReturn(test);

        // when
        TestResponseDTO testResponseDTO = testService.createTest(createTestRequestDTO);

        // then
        assertThat(testResponseDTO.getTestTitle()).isEqualTo(testTitle);
        assertThat(testResponseDTO.getTestContent()).isEqualTo(testContent);
    }

    @org.junit.jupiter.api.Test
    public void testGetTest() {
        // given
        Long testId = 1L;
        String testTitle = "Test Title A";
        String testContent = "Test Content A";
        Boolean testNullable = true;

        Test test = Test.builder()
                .testTitle(testTitle)
                .testContent(testContent)
                .testNullable(testNullable)
                .build();

        // when
        // JpaRepository 메소드로 test 객체 저장, 저장된 객체 반환 요청
        when(testRepository.save(any(Test.class))).thenReturn(test); // save 메서드의 모의 동작 설정
        when(testRepository.findById(testId)).thenReturn(Optional.of(test)); // findById 메서드의 모의 동작 설정

        TestResponseDTO testResponseDTO = testService.getTest(testId);

        // then
        // 반환 받은 객체의 title과 생성한 객체의 title 비교
        assertThat(testResponseDTO.getTestTitle()).isEqualTo(testTitle);
        assertThat(testResponseDTO.getTestContent()).isEqualTo(testContent);
    }
}
