package com.genzverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Blog;
import com.genzverse.entity.SavedBlog;
import com.genzverse.entity.User;

public interface SavedBlogRepository extends JpaRepository<SavedBlog, Long>
{
    Optional<SavedBlog> findByUserAndBlog(
            User user,
            Blog blog
    );

    List<SavedBlog> findByUser(User user);
    
    long countByUser(User user);
    
}