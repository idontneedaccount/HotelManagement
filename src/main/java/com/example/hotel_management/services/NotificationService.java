package com.example.hotel_management.services;

import com.example.hotel_management.dtos.NotificationDTO;

public interface NotificationService {
    void sendEmail(NotificationDTO notificationDTO);
}
