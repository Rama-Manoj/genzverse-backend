package com.genzverse.dto;

import java.time.LocalDateTime;

public class BlogResponse 
{
    private Long id;

    private String title;

    private String content;

    private String thumbnailUrl;

    private String author;

    private Long views;

    private Long likes;

    private Long comments;

    private Long shares;

    private LocalDateTime createdAt;
    
    public BlogResponse() {
		// TODO Auto-generated constructor stub
	}

    public BlogResponse(Long id,
                        String title,
                        String content,
                        String thumbnailUrl,
                        String author,
                        Long views,
                        Long likes,
                        Long comments,
                        Long shares,
                        LocalDateTime createdAt)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.author = author;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getAuthor() {
        return author;
    }

    public Long getViews() {
        return views;
    }

    public Long getLikes() {
        return likes;
    }

    public Long getComments() {
        return comments;
    }

    public Long getShares() {
        return shares;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}