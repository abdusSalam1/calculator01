package com.assignment.calculator;

import com.assignment.domain.Bill;
import com.assignment.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerTenureDiscountCalculator implements DiscountCalculator{

    @Value("${discount.customerTenure}")
    private BigDecimal discountPercentage ;

    @Override
    public BigDecimal calculate(Bill bill) {
        if (bill.getUser().isOldCustomer()) {
            return bill.getProducts().stream()
                    .filter(product -> !product.isGrocery())
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .multiply(discountPercentage);
        }
        return BigDecimal.ZERO;
    }
}
