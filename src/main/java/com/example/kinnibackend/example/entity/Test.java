package com.example.kinnibackend.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "test_id")
    private Long testId;

    @Column(name= "test_title", nullable = false)
    private String testTitle;

    @Column(name= "test_content", nullable = false)
    private String testContent;

    @Column(name= "test_nullable")
    private Boolean testNullable;

    @Builder
    public Test(String testTitle, String testContent, Boolean testNullable) {
        this.testTitle = testTitle;
        this.testContent = testContent;
        this.testNullable = testNullable;
    }

}
