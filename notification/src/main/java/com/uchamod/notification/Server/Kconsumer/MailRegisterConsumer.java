package com.uchamod.notification.Server.Kconsumer;

import com.uchamod.notification.Model.User;
import com.uchamod.notification.Server.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailRegisterConsumer {

    private static final Logger log = LoggerFactory.getLogger(MailConsumer.class);
    private final NotificationService emailService;
    StringBuilder body = new StringBuilder();

    @KafkaListener(topics = "user-mail-topic",groupId = "mail-group-id-v1")
    public void UserRegisterConnsumer(User event){
        log.info("🎯 Received new user infomation: {}", event.getUserId());
        log.info("User ID: {}, Email: {}", event.getUserId(), event.getUserEmail());
        try{
            emailService.sendUserRegisterEmail(event,body,"You are In Space - Congradulations #");
            System.out.println("sent user register mail");
        } catch (Exception e) {log.error("Failed to process stock update event: {}", event, e);
            throw new RuntimeException(e);

        }
    }
}
