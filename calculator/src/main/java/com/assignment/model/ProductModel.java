package com.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductModel {
    private String name;
    private BigDecimal price;
    private String category;
}
