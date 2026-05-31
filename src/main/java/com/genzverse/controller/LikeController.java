package com.genzverse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.service.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController 
{
    private final LikeService likeService;

    public LikeController(LikeService likeService)
    {
        this.likeService = likeService;
    }

    @PostMapping("/{blogId}")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long blogId)
    {
        return ResponseEntity.ok(
                likeService.toggleLike(blogId)
        );
    }
}