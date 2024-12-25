package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Product extends BaseEntity {
    private String name;
    private double price;
    private int stock;
}