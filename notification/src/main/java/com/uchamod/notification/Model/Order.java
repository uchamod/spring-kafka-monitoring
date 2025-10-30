package com.uchamod.notification.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private UUID orderId;
    private UUID customerId;
    private Double totalAmount;
    private String orderStatus="PENDING";
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date lastUpdate;

    private List<OrderProductModel> orderProductModelList;
}
