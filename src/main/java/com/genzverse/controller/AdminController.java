package com.genzverse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.entity.User;
import com.genzverse.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController 
{
    private final AdminService adminService;

    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    @DeleteMapping("/blogs/{blogId}")
    public ResponseEntity<String> deleteBlog(
            @PathVariable Long blogId)
    {
        return ResponseEntity.ok(
                adminService.deleteAnyBlog(blogId)
        );
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId)
    {
        return ResponseEntity.ok(
                adminService.deleteAnyComment(commentId)
        );
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(
                adminService.getAllUsers()
        );
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                adminService.deleteUser(userId)
        );
    }
}