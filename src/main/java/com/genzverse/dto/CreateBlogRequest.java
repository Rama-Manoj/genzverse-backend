package com.genzverse.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateBlogRequest 
{
    @NotBlank(message = "Title is required")
    private String title;

    @Size(
            min = 10,
            message = "Content must be at least 10 characters"
    )
    private String content;

    private String thumbnailUrl;
    
    private String category;
    
    private List<String> tags;
    
    public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public CreateBlogRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateBlogRequest(@NotBlank(message = "Title is required") String title,
			@Size(min = 10, message = "Content must be at least 10 characters") String content, String thumbnailUrl,
			String category) {
		super();
		this.title = title;
		this.content = content;
		this.thumbnailUrl = thumbnailUrl;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
	
    
}