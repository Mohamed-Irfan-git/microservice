package com.example.orderservice.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private Double price;

    // getters and setters
}