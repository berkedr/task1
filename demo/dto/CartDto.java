package com.example.demo.dto;


import lombok.Data;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private List<OrderItemDto> items;
    private double totalPrice;
}