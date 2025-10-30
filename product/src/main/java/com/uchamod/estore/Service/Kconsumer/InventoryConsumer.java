package com.uchamod.estore.Service.Kconsumer;

import com.uchamod.estore.Model.ProductInventoryEvent;
import com.uchamod.estore.Service.Product_Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


@Service
@RequiredArgsConstructor
public class InventoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    private final Product_Service productService;

    @KafkaListener(topics = "inventory-updates",groupId = "inventory-group-id-v2")
    public void consumerStockUpdateEvent(ProductInventoryEvent event){
        log.info("🎯 Received stock update event: {}", event);
        log.info("Product ID: {}, Quantity: {}", event.getProductId(), event.getProductCount());

        try{
            productService.updateAvailableCount(event);
           System.out.println("stock update event received");
       } catch (Exception e) {
           log.error("Failed to process stock update event: {}", event, e);
           throw new RuntimeException(e);
       }
    }
}
//to check current topics
//docker exec -it  confluentinc/cp-kafka:7.8.3 kafka-topics --bootstrap-server localhost:9092 --list