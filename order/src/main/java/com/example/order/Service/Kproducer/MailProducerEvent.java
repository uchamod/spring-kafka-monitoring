package com.example.order.Service.Kproducer;

import com.example.order.Model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailProducerEvent {

    private final KafkaTemplate<String, Order> kafkaTemplate;
    private static final String INVENTORY_TOPIC = "mail-topic";

    public void sendInventoryEvent(Order orderEvent){
        kafkaTemplate.send(INVENTORY_TOPIC,orderEvent.getOrderId().toString(),orderEvent).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Kafka send success: " + result.getRecordMetadata());
                    } else {
                        System.err.println("❌ Kafka send failed: " + ex.getMessage());
                    }
                }
        );
        System.out.println("Sent inventory update for product: " + orderEvent.getOrderId());

    }

}
