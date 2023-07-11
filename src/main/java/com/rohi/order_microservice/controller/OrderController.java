package com.rohi.order_microservice.controller;


import com.rohi.order_microservice.binding.Payment;
import com.rohi.order_microservice.binding.TransactionRequest;
import com.rohi.order_microservice.binding.TransactionResponse;
import com.rohi.order_microservice.model.Order;
import com.rohi.order_microservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;


    @PostMapping("/book")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request){
        return service.saveOrder(request);
        //make a REST API call to the payment microservice to receive payment for this order
    }


}
