package com.genzverse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.genzverse.entity.Blog;
import com.genzverse.entity.Comment;
import com.genzverse.entity.User;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.CommentRepository;
import com.genzverse.repository.UserRepository;

@Service
public class AdminService 
{
    private final BlogRepository blogRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public AdminService(BlogRepository blogRepository,
                        CommentRepository commentRepository,
                        UserRepository userRepository)
    {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public String deleteAnyBlog(Long blogId)
    {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found"));

        blogRepository.delete(blog);

        return "Blog deleted by admin";
    }

    public String deleteAnyComment(Long commentId)
    {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found"));

        commentRepository.delete(comment);

        return "Comment deleted by admin";
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public String deleteUser(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.delete(user);

        return "User deleted by admin";
    }
}