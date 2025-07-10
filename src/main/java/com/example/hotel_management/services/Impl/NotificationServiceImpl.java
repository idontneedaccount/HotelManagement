package com.example.hotel_management.services.Impl;

import com.example.hotel_management.dtos.NotificationDTO;
import com.example.hotel_management.entities.Notification;
import com.example.hotel_management.repositories.NotificationRepository;
import com.example.hotel_management.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;

    @Override
    @Async
    public void sendEmail(NotificationDTO notificationDTO) {
        log.info("Sending email");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(notificationDTO.getRecipient());
        mailMessage.setSubject(notificationDTO.getSubject());
        mailMessage.setText(notificationDTO.getBody());

        javaMailSender.send(mailMessage);
        Notification notification = Notification.builder()
                .recipient(notificationDTO.getRecipient())
                .subject(notificationDTO.getSubject())
                .body(notificationDTO.getBody())
                .bookingReference(notificationDTO.getBookingReference())
                .build();
        notificationRepository.save(notification);
    }
}
