package com.cafe.service.impl.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("No order was found having an id od " + id);
    }
}
