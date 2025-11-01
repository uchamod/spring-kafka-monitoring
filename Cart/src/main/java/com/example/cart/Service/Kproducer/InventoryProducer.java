package com.example.cart.Service.Kproducer;



import com.uchamod.commonmodules.DTO.ProductInventoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryProducer {

    private final KafkaTemplate<String, ProductInventoryEvent> kafkaTemplate;
    private static final String INVENTORY_TOPIC = "inventory-updates";

    public void sendInventoryEvent(ProductInventoryEvent productInventoryEvent){
        kafkaTemplate.send(INVENTORY_TOPIC,productInventoryEvent.getProductId().toString(),productInventoryEvent).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Kafka send success: " + result.getRecordMetadata());
                    } else {
                        System.err.println("❌ Kafka send failed: " + ex.getMessage());
                    }
                }
        );
        System.out.println("Sent inventory update for product: " + productInventoryEvent.getProductId());

    }
}
