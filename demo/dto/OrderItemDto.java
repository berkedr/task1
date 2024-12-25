package com.example.demo.dto;


import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private double priceAtPurchase;
    private int quantity;
}