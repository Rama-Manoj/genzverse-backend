package com.genzverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
    Optional<User> findByResetToken(String resetToken);
    
    Optional<User> findByVerificationToken(
            String verificationToken
    );
    
}