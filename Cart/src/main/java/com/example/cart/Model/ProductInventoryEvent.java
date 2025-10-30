package com.example.cart.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryEvent {
    private UUID productId;
    private Integer productCount;
    private Boolean isIncrese;

}
