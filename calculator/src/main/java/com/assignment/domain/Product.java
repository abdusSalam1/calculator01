package com.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.assignment.domain.ProductCategory.GROCERY;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class Product {
    private final String name;
    private final BigDecimal price;
    private final ProductCategory category;

    public boolean isGrocery(){
        return category.equals(GROCERY);
    }
}
