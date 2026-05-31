package com.genzverse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.dto.UserResponse;
import com.genzverse.service.FollowService;

@RestController
@RequestMapping("/api/follows")
public class FollowController 
{
    private final FollowService followService;

    public FollowController(FollowService followService)
    {
        this.followService = followService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> toggleFollow(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                followService.toggleFollow(userId)
        );
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserResponse>>
    getFollowers(@PathVariable Long userId)
    {
        return ResponseEntity.ok(
                followService.getFollowers(userId)
        );
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserResponse>>
    getFollowing(@PathVariable Long userId)
    {
        return ResponseEntity.ok(
                followService.getFollowing(userId)
        );
    }
}