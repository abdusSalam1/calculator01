package com.assignment.domain;

import com.assignment.manager.DiscountManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class Bill {

    private final User user;
    private final List<Product> products;
    private final String currency;

    public BillSummary createSummary(DiscountManager discountManager){
        BigDecimal totalDiscount = discountManager.calculateTotalDiscount(this);
        BigDecimal totalAmount = getTotalAmount();
        BigDecimal totalAfterDiscount = totalAmount.subtract(totalDiscount);
        return BillSummary.builder()
                .totalAmount(totalAmount)
                .discountApplied(totalDiscount)
                .finalAmount(totalAfterDiscount)
                .currency(currency)
                .build();
    }

    private BigDecimal getTotalAmount(){
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
