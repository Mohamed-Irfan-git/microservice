package shop.order_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.order_service.client.ProductClient;
import shop.order_service.client.dto.ProductResponse;
import shop.order_service.dto.OrderRequest;
import shop.order_service.entity.Order;
import shop.order_service.repository.OrderRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public Order createOrder(OrderRequest request) {

        // NETWORK CALL to another microservice
        ProductResponse product =
                productClient.getProductById(request.productId());

        BigDecimal totalPrice = product.price()
                .multiply(BigDecimal.valueOf(request.quantity()));

        Order order = Order.builder()
                .productId(product.id())
                .quantity(request.quantity())
                .totalPrice(totalPrice)
                .status("CREATED")
                .build();

        return orderRepository.save(order);
    }
}