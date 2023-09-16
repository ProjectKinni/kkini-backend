//package com.example.kinnibackend.example;
//
//import com.example.kinnibackend.entity.Test;
//import com.example.kinnibackend.repository.example.TestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class TestRepositoryTests {
//
//    @Autowired
//    private TestRepository testRepository;
//
//    // entity 이름이 Test라 import를 이런식으로 해와야 같이 쓸 수 있음
//    // 실제 테스트코드 작성 때는 import에 적어주고 @Test 어노테이션 사용
//    @org.junit.jupiter.api.Test
//    public void testSaveAndFind() {
//        // given
//        // test 객체 생성
//        String testTitle = "Test Title A";
//        String testContent = "Test Content A";
//        Boolean testNullable = true;
//
//        Test test = Test.builder()
//                .testTitle(testTitle)
//                .testContent(testContent)
//                .testNullable(testNullable)
//                .build();
//
//        // when
//        // JpaRepository 메소드로 test 객체 저장, 저장된 객체 반환 요청
//        Test savedTest = testRepository.save(test);
//        Optional<Test> foundTest = testRepository.findById(savedTest.getTestId());
//
//        // then
//        // 반환 받은 객체의 title과 생성한 객체의 title 비교
//        assertThat(foundTest.get().getTestTitle()).isEqualTo(testTitle);
//    }
//
//}
