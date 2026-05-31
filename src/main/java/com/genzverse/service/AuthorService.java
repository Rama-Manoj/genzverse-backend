package com.genzverse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.genzverse.dto.AuthorBlogResponse;
import com.genzverse.dto.AuthorProfileResponse;
import com.genzverse.entity.Blog;
import com.genzverse.entity.User;
import com.genzverse.exception.ResourceNotFoundException;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.FollowRepository;
import com.genzverse.repository.UserRepository;

@Service
public class AuthorService
{
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final FollowRepository followRepository;

    public AuthorService(
            UserRepository userRepository,
            BlogRepository blogRepository,
            FollowRepository followRepository)
    {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.followRepository = followRepository;
    }

    public AuthorProfileResponse getAuthor(
            Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Author not found"
                        ));

        List<Blog> blogs =
                blogRepository.findByUser(user);

        List<AuthorBlogResponse> blogResponses =
                blogs.stream()
                        .filter(blog -> !blog.isDeleted())
                        .map(blog ->
                                new AuthorBlogResponse(
                                        blog.getId(),
                                        blog.getTitle(),
                                        blog.getSlug()
                                ))
                        .toList();

        long followers =
                followRepository.countByFollowing(user);

        long following =
                followRepository.countByFollower(user);

        return new AuthorProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getBio(),
                followers,
                following,
                blogResponses.size(),
                blogResponses
        );
    }
}