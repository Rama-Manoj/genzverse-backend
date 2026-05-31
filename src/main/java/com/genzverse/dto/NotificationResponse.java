package com.genzverse.dto;

import java.time.LocalDateTime;

public class NotificationResponse 
{
    private Long id;

    private String message;

    private String type;

    private boolean isRead;

    private LocalDateTime createdAt;

    public NotificationResponse(Long id,
                                String message,
                                String type,
                                boolean isRead,
                                LocalDateTime createdAt)
    {
        this.id = id;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}