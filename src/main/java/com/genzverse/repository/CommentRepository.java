package com.genzverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Blog;
import com.genzverse.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    List<Comment> findByBlog(Blog blog);
}