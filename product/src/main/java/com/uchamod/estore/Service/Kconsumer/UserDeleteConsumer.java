package com.uchamod.estore.Service.Kconsumer;



import com.uchamod.commonmodules.Models.UserDeleteEvent;
import com.uchamod.estore.Service.Product_Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDeleteConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserDeleteConsumer.class);

    private final Product_Service productService;

    @KafkaListener(topics = "user-delete-topic",groupId = "inventory-group-id-v2")
    public void productsDeleteEvent(UserDeleteEvent event){
        log.info("🎯 Received user delete event: {}", event);
        log.info("User ID: {}", event.getUserId().toString());

        try{
            productService.deleteAllProducts(event.getUserId());
            System.out.println("delete products");
        } catch (Exception e) {
            log.error("Failed to process stock update event: {}", event, e);
            throw new RuntimeException(e);
        }
    }
}
