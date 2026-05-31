package com.genzverse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.dto.BlogResponse;
import com.genzverse.service.SavedBlogService;

@RestController
@RequestMapping("/api/saved")
public class SavedBlogController 
{
    private final SavedBlogService savedBlogService;

    public SavedBlogController(
            SavedBlogService savedBlogService)
    {
        this.savedBlogService = savedBlogService;
    }

    @PostMapping("/{blogId}")
    public ResponseEntity<String> toggleSave(
            @PathVariable Long blogId)
    {
        return ResponseEntity.ok(
                savedBlogService.toggleSave(blogId)
        );
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>>
    getSavedBlogs()
    {
        return ResponseEntity.ok(
                savedBlogService.getSavedBlogs()
        );
    }
}