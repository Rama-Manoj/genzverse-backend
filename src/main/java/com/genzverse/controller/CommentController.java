package com.genzverse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.dto.CommentResponse;
import com.genzverse.dto.CreateCommentRequest;
import com.genzverse.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController 
{
    private final CommentService commentService;

    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @PostMapping("/{blogId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long blogId,
            @RequestBody CreateCommentRequest request)
    {
        return new ResponseEntity<>(
                commentService.addComment(blogId, request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<List<CommentResponse>>
    getCommentsByBlog(@PathVariable Long blogId)
    {
        return ResponseEntity.ok(
                commentService.getCommentsByBlog(blogId)
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId)
    {
        return ResponseEntity.ok(
                commentService.deleteComment(commentId)
        );
    }
}