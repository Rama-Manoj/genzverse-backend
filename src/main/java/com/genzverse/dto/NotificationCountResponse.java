package com.genzverse.dto;

public class NotificationCountResponse
{
    private long unreadCount;

    public NotificationCountResponse(
            long unreadCount)
    {
        this.unreadCount = unreadCount;
    }

    public long getUnreadCount()
    {
        return unreadCount;
    }
}