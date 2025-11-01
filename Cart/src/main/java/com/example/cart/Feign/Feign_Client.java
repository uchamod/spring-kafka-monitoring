package com.example.cart.Feign;



import com.example.cart.Model.ProductWrapper;
import com.uchamod.commonmodules.DTO.CountUpdater;
import com.uchamod.commonmodules.DTO.ProductData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient("PRODUCT-SERVICE")
public interface  Feign_Client {
    @GetMapping("/api/products/getTotal/{productIds}")
    ResponseEntity<ProductData> getTotalAmount(@PathVariable UUID productIds);

    @GetMapping("/api/products/getProductById/{productId}")
    ResponseEntity<ProductWrapper> getProductById(@PathVariable UUID productId);

    @PutMapping("/api/products/updateAvailableCount")
    void updateAvailableCount(@RequestBody CountUpdater countUpdater);
}
