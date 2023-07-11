package com.rohi.order_microservice.service;

import com.rohi.order_microservice.binding.Payment;
import com.rohi.order_microservice.binding.TransactionRequest;
import com.rohi.order_microservice.binding.TransactionResponse;
import com.rohi.order_microservice.exception.OrderCreationFailure;
import com.rohi.order_microservice.model.Order;
import com.rohi.order_microservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate template;

    public TransactionResponse saveOrder(TransactionRequest request) {

        Order order = request.getOrder();
        Payment payment = request.getPayment();
        // Save the order entity using the repository to generate its ID
        order = repository.save(order);

        // Set the generated order ID in the payment object
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        Payment paymentResponse = template.postForObject("http://payment-service/payment/pay", payment, Payment.class);
        String response = paymentResponse.getStatus().equals("success") ? "Payment is successful. Your order has" +
                " been placed" : "Payment failed. Order added to cart";
        // Save the order entity using the repository

        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);


    }
}
