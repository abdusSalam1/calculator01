package com.assignment.calculator;

import com.assignment.domain.Bill;
import com.assignment.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class AffiliateDiscountCalculator implements DiscountCalculator {

    @Value("${discount.affiliate}")
    private BigDecimal discountPercentage;

    @Override
    public BigDecimal calculate(Bill bill) {
        if (bill.getUser().isAffiliate()) {
            BigDecimal totalDiscountable = bill.getProducts().stream()
                    .filter(product -> !product.isGrocery())
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return totalDiscountable.multiply(discountPercentage).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
}
