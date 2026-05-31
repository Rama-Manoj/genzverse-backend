package com.genzverse.dto;

import java.util.List;

public class AuthorProfileResponse
{
    private Long id;
    private String username;
    private String bio;

    private long followers;
    private long following;
    private long totalBlogs;

    private List<AuthorBlogResponse> blogs;

    public AuthorProfileResponse(
            Long id,
            String username,
            String bio,
            long followers,
            long following,
            long totalBlogs,
            List<AuthorBlogResponse> blogs)
    {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.followers = followers;
        this.following = following;
        this.totalBlogs = totalBlogs;
        this.blogs = blogs;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public long getFollowers() { return followers; }
    public long getFollowing() { return following; }
    public long getTotalBlogs() { return totalBlogs; }
    public List<AuthorBlogResponse> getBlogs() { return blogs; }
}