package com.assignment.calculator;

import com.assignment.domain.Bill;
import com.assignment.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FlatDiscountCalculator implements DiscountCalculator{

    @Value("${discount.flat}")
    private BigDecimal discountPercentage;

    @Override
    public BigDecimal calculate(Bill bill) {
        BigDecimal totalBill = bill.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountAmount = BigDecimal.valueOf(Math.floor(totalBill.doubleValue() / 100)).multiply(discountPercentage);

        return discountAmount.setScale(2, RoundingMode.HALF_UP);
    }
}
