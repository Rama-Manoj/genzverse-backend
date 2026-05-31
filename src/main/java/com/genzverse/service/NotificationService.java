package com.genzverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.NotificationCountResponse;
import com.genzverse.dto.NotificationResponse;
import com.genzverse.entity.Blog;
import com.genzverse.entity.Notification;
import com.genzverse.entity.NotificationType;
import com.genzverse.entity.User;
import com.genzverse.exception.ResourceNotFoundException;
import com.genzverse.repository.NotificationRepository;
import com.genzverse.repository.UserRepository;

@Service
public class NotificationService 
{
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository)
    {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(
            User sender,
            User receiver,
            Blog blog,
            NotificationType type,
            String message)
    {
        if(sender.getId().equals(receiver.getId()))
        {
            return;
        }

        Notification notification = new Notification();

        notification.setSender(sender);

        notification.setReceiver(receiver);

        notification.setBlog(blog);

        notification.setType(type);

        notification.setMessage(message);

        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getMyNotifications()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return notificationRepository
                .findByReceiverOrderByCreatedAtDesc(user)
                .stream()
                .map(notification ->
                        new NotificationResponse(
                                notification.getId(),
                                notification.getMessage(),
                                notification.getType().name(),
                                notification.isRead(),
                                notification.getCreatedAt()
                        )
                )
                .toList();
    }

    public String markAsRead(Long notificationId)
    {
        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notification.setRead(true);

        notificationRepository.save(notification);

        return "Notification marked as read";
    }
    
    public NotificationCountResponse
    getUnreadCount()
    {
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        long count =
                notificationRepository
                        .countByReceiverAndReadFalse(
                                user
                        );

        return new NotificationCountResponse(
                count
        );
    }
    
}