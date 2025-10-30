package com.uchamod.notification.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductModel {
    private UUID productId;
    private UUID sellerId;
    private Integer productCount;
    private Double productItemPrice;
}
