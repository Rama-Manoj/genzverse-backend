package com.genzverse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genzverse.dto.BlogResponse;
import com.genzverse.dto.CreateBlogRequest;
import com.genzverse.dto.ShareResponse;
import com.genzverse.service.BlogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/blogs")
public class BlogController 
{
    private final BlogService blogService;

    public BlogController(BlogService blogService)
    {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
         @Valid   @RequestBody CreateBlogRequest request)
    {
        BlogResponse response =
                blogService.createBlog(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAllBlogs()
    {
        return ResponseEntity.ok(
                blogService.getAllBlogs()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(
                blogService.getBlogById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @RequestBody CreateBlogRequest request)
    {
        return ResponseEntity.ok(
                blogService.updateBlog(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(
                blogService.deleteBlog(id)
        );
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<List<BlogResponse>>
    searchByTitle(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "desc")
            String sort)
    {
        return ResponseEntity.ok(
                blogService.searchBlogsByTitle(
                        keyword,
                        page,
                        size,
                        sort
                )
        );
    }
    
    @GetMapping("/search/content")
    public ResponseEntity<List<BlogResponse>>
    searchByContent(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "desc")
            String sort)
    {
        return ResponseEntity.ok(
                blogService.searchBlogsByContent(
                        keyword,
                        page,
                        size,
                        sort
                )
        );
    }
    
    
    @GetMapping("/trending/views")
    public ResponseEntity<List<BlogResponse>>
    getTrendingByViews()
    {
        return ResponseEntity.ok(
                blogService.getTrendingByViews()
        );
    }
    
    @GetMapping("/trending/likes")
    public ResponseEntity<List<BlogResponse>>
    getTrendingByLikes()
    {
        return ResponseEntity.ok(
                blogService.getTrendingByLikes()
        );
    }
    
    @GetMapping("/trending/comments")
    public ResponseEntity<List<BlogResponse>>
    getTrendingByComments()
    {
        return ResponseEntity.ok(
                blogService.getTrendingByComments()
        );
    }
    
    @PostMapping("/{blogId}/share")
    public ResponseEntity<ShareResponse>
    shareBlog(
            @PathVariable Long blogId)
    {
        return ResponseEntity.ok(
                blogService.shareBlog(blogId)
        );
    }
    
    @GetMapping("/public/{id}")
    public ResponseEntity<BlogResponse>
    getPublicBlog(
            @PathVariable Long id)
    {
        return ResponseEntity.ok(
                blogService.getBlogById(id)
        );
    }
    
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<BlogResponse>>
    getBlogsByCategory(
            @PathVariable String categoryName)
    {
        return ResponseEntity.ok(
                blogService.getBlogsByCategory(
                        categoryName
                )
        );
    }
    
    
    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<BlogResponse>>
    getBlogsByTag(
            @PathVariable String tagName)
    {
        return ResponseEntity.ok(
                blogService.getBlogsByTag(tagName)
        );
    }
    
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<BlogResponse>
    getBlogBySlug(
            @PathVariable String slug)
    {
        return ResponseEntity.ok(
                blogService.getBlogBySlug(slug)
        );
    }
    
}