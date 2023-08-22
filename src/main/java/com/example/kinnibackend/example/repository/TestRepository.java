package com.example.kinnibackend.example.repository;

import com.example.kinnibackend.example.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
