package com.uchamod.notification.Server.Kconsumer;


import com.uchamod.commonmodules.DTO.EmailDTO;
import com.uchamod.commonmodules.DTO.UserWrapper;
import com.uchamod.commonmodules.Models.Order;

import com.uchamod.notification.Feign.UserFeignClient;
import com.uchamod.notification.Helper.Helper;

import com.uchamod.notification.Server.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailConsumer {

    private static final Logger log = LoggerFactory.getLogger(MailConsumer.class);
    private final Helper helper;
    private final UserFeignClient userFeignClient;
    private final NotificationService emailService;
    StringBuilder body = new StringBuilder();
    @KafkaListener(topics = "mail-topic",groupId = "mail-group-id-v1")
    public void consumerStockUpdateEvent(Order event){
        log.info("🎯 Received order create event: {}", event.getOrderId());
        log.info("Order ID: {}, Amount: {}", event.getOrderId(), event.getTotalAmount());

        try{
            List<EmailDTO> emailDTOList= helper.createEmailDTO(event.getOrderProductModelList());
            System.out.println(emailDTOList);
            ResponseEntity<UserWrapper> customerDTO= userFeignClient.getUserDTO(event.getCustomerId());
            for(EmailDTO emailDTO : emailDTOList){
                ResponseEntity<UserWrapper> sellerDTO= userFeignClient.getUserDTO(emailDTO.getSellerId());

                emailService.sendOrderNotificationToSeller(sellerDTO.getBody(),event,customerDTO.getBody(),emailDTO,body,"New Order Received - Order #");

            }
            System.out.println("order create mail sent to seller");
        } catch (Exception e) {
            log.error("Failed to process stock update event: {}", event, e);
            throw new RuntimeException(e);
        }
    }
}
