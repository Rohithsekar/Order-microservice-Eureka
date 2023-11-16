package com.rohi.order_microservice.proxy;


import com.rohi.order_microservice.binding.Payment;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="PAYMENT-SERVICE", url = "http://localhost:9090/payment", fallback = PaymentFallback.class)
public interface PaymentProxy {

    @PostMapping("/pay")
    public Payment makePayment(@RequestBody Payment payment);


}
