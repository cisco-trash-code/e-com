package com.cisco.orderservice.service;

import com.cisco.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
