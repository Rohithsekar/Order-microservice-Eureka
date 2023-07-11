package com.rohi.order_microservice.binding;

import com.rohi.order_microservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionRequest {

    private Order order;
    private Payment payment;
}
