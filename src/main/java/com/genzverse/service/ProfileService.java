package com.genzverse.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.UpdateProfileRequest;
import com.genzverse.dto.UserProfileResponse;
import com.genzverse.entity.User;
import com.genzverse.exception.ResourceNotFoundException;
import com.genzverse.repository.UserRepository;

@Service
public class ProfileService
{
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public UserProfileResponse getMyProfile()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    public UserProfileResponse updateMyProfile(
            UpdateProfileRequest request)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setBio(request.getBio());
        user.setProfileImage(request.getProfileImage());
        user.setWebsite(request.getWebsite());
        user.setLinkedinUrl(request.getLinkedinUrl());
        user.setGithubUrl(request.getGithubUrl());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public UserProfileResponse getProfileByUserId(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    private UserProfileResponse mapToResponse(User user)
    {
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getProfileImage(),
                user.getWebsite(),
                user.getLinkedinUrl(),
                user.getGithubUrl()
        );
    }
}