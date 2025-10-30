package com.example.cart.Service.Kproducer;


import com.example.cart.Model.UserDeleteEvent;
import com.example.cart.Service.CartService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDeleteConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserDeleteConsumer.class);

    private final CartService cartService;

    @KafkaListener(topics = "user-delete-topic",groupId = "inventory-group-id-v2")
    public void productsDeleteEvent(UserDeleteEvent event){
        log.info("🎯 Received cart delete event: {}", event);
        log.info("User ID: {}", event.getUserId().toString());

        try{
            cartService.checkoutFromCart(event.getUserId());
            System.out.println("manipulate cart table");
        } catch (Exception e) {
            log.error("Failed to process cart delete event: {}", event, e);
            throw new RuntimeException(e);
        }
    }
}
