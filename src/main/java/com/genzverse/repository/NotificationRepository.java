package com.genzverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Notification;
import com.genzverse.entity.User;

public interface NotificationRepository
extends JpaRepository<Notification, Long>
{
List<Notification> findByReceiverOrderByCreatedAtDesc(
    User receiver
);

long countByReceiverAndReadFalse(
    User receiver
);

List<Notification> findByReceiver(
    User receiver
);
}