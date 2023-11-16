package com.rohi.order_microservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rohi.order_microservice.binding.Payment;
import com.rohi.order_microservice.binding.TransactionRequest;
import com.rohi.order_microservice.binding.TransactionResponse;
import com.rohi.order_microservice.model.Order;
import com.rohi.order_microservice.service.OrderService;
import io.github.resilience4j.retry.annotation.Retry;
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

    public static int counter = 1;


    @PostMapping("/book")
    @Retry(name = "payment-service", fallbackMethod = "orderfallback")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        System.out.println("book order got invoked for " + counter + " time");
        counter++;
        return service.saveOrder(request);
        //make a REST API call to the payment microservice to receive payment for this order
    }

    public String orderfallback(TransactionRequest request, Exception e){
        return "we are facing server issues. Please try again later. Exception is " + e.toString();
    }


}
