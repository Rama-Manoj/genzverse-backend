package com.genzverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.UserResponse;
import com.genzverse.entity.Follow;
import com.genzverse.entity.NotificationType;
import com.genzverse.entity.User;
import com.genzverse.repository.FollowRepository;
import com.genzverse.repository.UserRepository;

@Service
public class FollowService 
{
    private final FollowRepository followRepository;

    private final UserRepository userRepository;
    
    private final NotificationService notificationService;


    
    
    public FollowService(FollowRepository followRepository, UserRepository userRepository,
			NotificationService notificationService) {
		super();
		this.followRepository = followRepository;
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}

	public String toggleFollow(Long userId)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User follower = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        User following = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Target user not found"));

        if(follower.getId().equals(following.getId()))
        {
            throw new RuntimeException(
                    "You cannot follow yourself"
            );
        }

        Follow existingFollow = followRepository
                .findByFollowerAndFollowing(
                        follower,
                        following
                )
                .orElse(null);

        if(existingFollow != null)
        {
            followRepository.delete(existingFollow);

            return "User unfollowed";
        }

        Follow follow = new Follow();

        follow.setFollower(follower);

        follow.setFollowing(following);

        follow.setFollowedAt(LocalDateTime.now());

        followRepository.save(follow);
        
        notificationService.createNotification(
                follower,
                following,
                null,
                NotificationType.FOLLOW,
                follower.getUsername() + " started following you"
        );

        return "User followed";
    }

    public List<UserResponse> getFollowers(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return followRepository.findByFollowing(user)
                .stream()
                .map(follow ->
                        new UserResponse(
                                follow.getFollower().getId(),
                                follow.getFollower().getUsername(),
                                follow.getFollower().getEmail()
                        )
                )
                .toList();
    }

    public List<UserResponse> getFollowing(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return followRepository.findByFollower(user)
                .stream()
                .map(follow ->
                        new UserResponse(
                                follow.getFollowing().getId(),
                                follow.getFollowing().getUsername(),
                                follow.getFollowing().getEmail()
                        )
                )
                .toList();
    }
}