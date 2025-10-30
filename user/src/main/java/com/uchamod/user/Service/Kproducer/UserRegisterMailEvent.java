package com.uchamod.user.Service.Kproducer;


import com.uchamod.user.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterMailEvent {

    private final KafkaTemplate<String, User> kafkaTemplate;
    private static final String INVENTORY_TOPIC = "user-mail-topic";

    public void sendInventoryEvent(User userEvent){
        kafkaTemplate.send(INVENTORY_TOPIC,userEvent.getUserId().toString(),userEvent).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Kafka send success: " + result.getRecordMetadata());
                    } else {
                        System.err.println("❌ Kafka send failed: " + ex.getMessage());
                    }
                }
        );
        System.out.println("Sent inventory update for product: " + userEvent.getUserId());

    }
}
