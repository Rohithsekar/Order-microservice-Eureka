package com.rohi.order_microservice.proxy;

import com.rohi.order_microservice.binding.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallback implements PaymentProxy{
    @Override
    public Payment makePayment(Payment payment) {
        /** You define how the fallback response should be */
        return null;
    }
}
