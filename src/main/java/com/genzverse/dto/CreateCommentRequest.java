package com.genzverse.dto;

public class CreateCommentRequest 
{
    private String content;

    private Long parentCommentId;

    public String getContent() {
        return content;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}