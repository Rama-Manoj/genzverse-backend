package com.genzverse.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blogs")
@Getter
@Setter
public class Blog implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    private String thumbnailUrl;

    private Long views = 0L;

    private Long likeCount = 0L;

    private Long commentCount = 0L;

    private Long shareCount = 0L;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @JsonIgnore
    @OneToMany(
            mappedBy = "blog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "blog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Like> likes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "blog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SavedBlog> savedBlogs = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "blog",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Notification> notifications = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    
    @ManyToMany
    @JoinTable(
            name = "blog_tags",
            joinColumns =
                    @JoinColumn(name = "blog_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    @Column(unique = true)
    private String slug;
    
    private boolean deleted = false;
    
    public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public String getSlug() {
		return slug;
	}


	public void setSlug(String slug) {
		this.slug = slug;
	}


	public Blog() {
		// TODO Auto-generated constructor stub
	}


	public Blog(Long id, String title, String content, String thumbnailUrl, Long views, Long likeCount,
			Long commentCount, Long shareCount, LocalDateTime createdAt, LocalDateTime updatedAt, User user,
			List<Comment> comments, List<Like> likes, List<SavedBlog> savedBlogs, List<Notification> notifications,
			Category category) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.thumbnailUrl = thumbnailUrl;
		this.views = views;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.shareCount = shareCount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.comments = comments;
		this.likes = likes;
		this.savedBlogs = savedBlogs;
		this.notifications = notifications;
		this.category = category;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getThumbnailUrl() {
		return thumbnailUrl;
	}


	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}


	public Long getViews() {
		return views;
	}


	public void setViews(Long views) {
		this.views = views;
	}


	public Long getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}


	public Long getCommentCount() {
		return commentCount;
	}


	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}


	public Long getShareCount() {
		return shareCount;
	}


	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public List<Like> getLikes() {
		return likes;
	}


	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}


	public List<SavedBlog> getSavedBlogs() {
		return savedBlogs;
	}


	public void setSavedBlogs(List<SavedBlog> savedBlogs) {
		this.savedBlogs = savedBlogs;
	}


	public List<Notification> getNotifications() {
		return notifications;
	}


	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}
    
	public Set<Tag> getTags()
	{
	    return tags;
	}

	public void setTags(Set<Tag> tags)
	{
	    this.tags = tags;
	}
	
    
}