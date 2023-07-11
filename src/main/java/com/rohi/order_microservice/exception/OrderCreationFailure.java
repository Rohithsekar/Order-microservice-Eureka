package com.rohi.order_microservice.exception;

public class OrderCreationFailure extends RuntimeException {

    public OrderCreationFailure(String message){
        super(message);
    }

}
