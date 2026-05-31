package com.genzverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Blog;
import com.genzverse.entity.Like;
import com.genzverse.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long>
{
    Optional<Like> findByUserAndBlog(User user, Blog blog);

    long countByBlog(Blog blog);
}