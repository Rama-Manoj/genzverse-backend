package com.genzverse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.dto.UpdateProfileRequest;
import com.genzverse.dto.UserProfileResponse;
import com.genzverse.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController
{
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService)
    {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile()
    {
        return ResponseEntity.ok(
                profileService.getMyProfile()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            @RequestBody UpdateProfileRequest request)
    {
        return ResponseEntity.ok(
                profileService.updateMyProfile(request)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getProfile(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                profileService.getProfileByUserId(userId)
        );
    }
}