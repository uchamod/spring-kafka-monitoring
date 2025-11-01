package com.uchamod.user.Service.Kproducer;


import com.uchamod.commonmodules.Models.UserDeleteEvent;

import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDeleteProducer {

    private final KafkaTemplate<String, UserDeleteEvent> kafkaTemplate;
    private static final String INVENTORY_TOPIC = "user-delete-topic";

    public void sendUserDeleteEvent(UUID userId){

        UserDeleteEvent userDeleteEvent=new UserDeleteEvent();
        kafkaTemplate.send(INVENTORY_TOPIC,userId.toString(),userDeleteEvent).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Kafka send success: " + result.getRecordMetadata());
                    } else {
                        System.err.println("❌ Kafka send failed: " + ex.getMessage());
                    }
                }
        );
        System.out.println("Sent userdata to consumers: " + userDeleteEvent.getUserId().toString());

    }
}
