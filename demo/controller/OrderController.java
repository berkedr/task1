package com.example.demo.controller;


import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{cartId}/place")
    public Order placeOrder(@PathVariable Long cartId) {
        return orderService.placeOrder(cartId);
    }

    @GetMapping("/{orderId}")
    public Order getOrderForCode(@PathVariable Long orderId) {
        return orderService.getOrderForCode(orderId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getAllOrdersForCustomer(@PathVariable Long customerId) {
        return orderService.getAllOrdersForCustomer(customerId);
    }
}