package com.example.hotel_management.repositories;

import com.example.hotel_management.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
