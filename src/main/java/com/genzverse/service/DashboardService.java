package com.genzverse.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.DashboardResponse;
import com.genzverse.entity.Blog;
import com.genzverse.entity.User;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.FollowRepository;
import com.genzverse.repository.SavedBlogRepository;
import com.genzverse.repository.UserRepository;

@Service
public class DashboardService
{
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final FollowRepository followRepository;
    private final SavedBlogRepository savedBlogRepository;

    public DashboardService(
            UserRepository userRepository,
            BlogRepository blogRepository,
            FollowRepository followRepository,
            SavedBlogRepository savedBlogRepository)
    {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.followRepository = followRepository;
        this.savedBlogRepository = savedBlogRepository;
    }

    public DashboardResponse getDashboard()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Blog> blogs = blogRepository.findByUser(user);

        long totalBlogs = blogs.size();

        long totalViews = blogs.stream()
                .mapToLong(Blog::getViews)
                .sum();

        long totalLikes = blogs.stream()
                .mapToLong(Blog::getLikeCount)
                .sum();

        long totalComments = blogs.stream()
                .mapToLong(Blog::getCommentCount)
                .sum();

        long followers =
                followRepository.countByFollowing(user);

        long following =
                followRepository.countByFollower(user);

        long savedBlogs =
                savedBlogRepository.countByUser(user);

        return new DashboardResponse(
                totalBlogs,
                totalViews,
                totalLikes,
                totalComments,
                followers,
                following,
                savedBlogs
        );
    }
}