package com.example.kinnibackend.repository.user;


import com.example.kinnibackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    Users findByUserId(Long userId);

    void deleteByUserId(Long userId);

    boolean existsByNickname(String nickname);
}