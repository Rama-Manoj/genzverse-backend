package com.genzverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Category;

public interface CategoryRepository
        extends JpaRepository<Category, Long>
{
    Optional<Category> findByName(String name);
}