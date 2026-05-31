package com.genzverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.BlogResponse;
import com.genzverse.entity.Blog;
import com.genzverse.entity.SavedBlog;
import com.genzverse.entity.User;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.SavedBlogRepository;
import com.genzverse.repository.UserRepository;

@Service
public class SavedBlogService 
{
    private final SavedBlogRepository savedBlogRepository;

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    public SavedBlogService(
            SavedBlogRepository savedBlogRepository,
            BlogRepository blogRepository,
            UserRepository userRepository)
    {
        this.savedBlogRepository = savedBlogRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public String toggleSave(Long blogId)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found"));

        SavedBlog existingSave = savedBlogRepository
                .findByUserAndBlog(user, blog)
                .orElse(null);

        if(existingSave != null)
        {
            savedBlogRepository.delete(existingSave);

            return "Blog removed from saved list";
        }

        SavedBlog savedBlog = new SavedBlog();

        savedBlog.setUser(user);

        savedBlog.setBlog(blog);

        savedBlog.setSavedAt(LocalDateTime.now());

        savedBlogRepository.save(savedBlog);

        return "Blog saved successfully";
    }

    public List<BlogResponse> getSavedBlogs()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return savedBlogRepository.findByUser(user)
                .stream()
                .map(saved ->
                        new BlogResponse(
                                saved.getBlog().getId(),
                                saved.getBlog().getTitle(),
                                saved.getBlog().getContent(),
                                saved.getBlog().getThumbnailUrl(),
                                saved.getBlog().getUser().getUsername(),
                                saved.getBlog().getViews(),
                                saved.getBlog().getLikeCount(),
                                saved.getBlog().getCommentCount(),
                                saved.getBlog().getShareCount(),
                                saved.getBlog().getCreatedAt()
                        )
                )
                .toList();
    }
}