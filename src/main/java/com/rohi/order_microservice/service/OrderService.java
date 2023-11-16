package com.rohi.order_microservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohi.order_microservice.binding.Payment;
import com.rohi.order_microservice.binding.TransactionRequest;
import com.rohi.order_microservice.binding.TransactionResponse;
import com.rohi.order_microservice.exception.OrderCreationFailure;
import com.rohi.order_microservice.model.Order;
import com.rohi.order_microservice.proxy.PaymentProxy;
import com.rohi.order_microservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    @Lazy
    private RestTemplate template;

    @Autowired
    private PaymentProxy paymentProxy;


    private Logger log = LoggerFactory.getLogger(OrderService.class);

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URI;

    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {

        Order order = request.getOrder();
        Payment payment = request.getPayment();
        // Save the order entity using the repository to generate its ID
        order = repository.save(order);

        // Set the generated order ID in the payment object
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        log.info("OrderService request : {}", new ObjectMapper().writeValueAsString(request));
//        Payment paymentResponse = template.postForObject(ENDPOINT_URI, payment, Payment.class);
        Payment paymentResponse = paymentProxy.makePayment(payment);
        log.info("Payment-Service response to OrderService API call : {}", new ObjectMapper().writeValueAsString(paymentResponse));
        String response = paymentResponse.getStatus().equals("success") ? "Payment is successful. Your order has" +
                " been placed" : "Payment failed. Order added to cart";
        // Save the order entity using the repository

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);

    }
}