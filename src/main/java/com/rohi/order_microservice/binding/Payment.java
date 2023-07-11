package com.rohi.order_microservice.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {

    private int id;
    private String status;
    private String transactionId;
    private int orderId;
    private double amount;
}
