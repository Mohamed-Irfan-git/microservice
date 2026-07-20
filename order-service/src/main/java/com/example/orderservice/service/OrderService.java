package com.example.orderservice.service;

import com.example.orderservice.feign.ProductClient;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository repository;

    public Order createOrder(Long productId, Integer quantity) {

        ProductResponse product = productClient.getProduct(productId);

        Order order = new Order();

        order.setProductId(productId);
        order.setQuantity(quantity);

        double total = product.getPrice() * quantity;
        order.setTotalPrice(total);
        return repository.save(order);
    }

}