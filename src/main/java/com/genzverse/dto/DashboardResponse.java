package com.genzverse.dto;

public class DashboardResponse
{
    private long totalBlogs;
    private long totalViews;
    private long totalLikes;
    private long totalComments;
    private long followers;
    private long following;
    private long savedBlogs;

    public DashboardResponse(
            long totalBlogs,
            long totalViews,
            long totalLikes,
            long totalComments,
            long followers,
            long following,
            long savedBlogs)
    {
        this.totalBlogs = totalBlogs;
        this.totalViews = totalViews;
        this.totalLikes = totalLikes;
        this.totalComments = totalComments;
        this.followers = followers;
        this.following = following;
        this.savedBlogs = savedBlogs;
    }

    public long getTotalBlogs() {
        return totalBlogs;
    }

    public long getTotalViews() {
        return totalViews;
    }

    public long getTotalLikes() {
        return totalLikes;
    }

    public long getTotalComments() {
        return totalComments;
    }

    public long getFollowers() {
        return followers;
    }

    public long getFollowing() {
        return following;
    }

    public long getSavedBlogs() {
        return savedBlogs;
    }
}