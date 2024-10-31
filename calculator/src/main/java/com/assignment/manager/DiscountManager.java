package com.assignment.manager;

import com.assignment.calculator.*;
import com.assignment.domain.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountManager {

    private final EmployeeDiscountCalculator employeeDiscountCalculator;
    private final AffiliateDiscountCalculator affiliateDiscountCalculator;
    private final CustomerTenureDiscountCalculator customerTenureDiscountCalculator;
    private final FlatDiscountCalculator flatDiscountCalculator;

    public BigDecimal calculateTotalDiscount(Bill bill) {
        List<DiscountCalculator> discountCalculators = Arrays.asList(employeeDiscountCalculator, affiliateDiscountCalculator,
                customerTenureDiscountCalculator, flatDiscountCalculator);
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal highestPercentageDiscount = BigDecimal.ZERO;

        for (DiscountCalculator calculator : discountCalculators) {
            BigDecimal discount = calculator.calculate(bill);

            if (calculator instanceof FlatDiscountCalculator) {
                totalDiscount = totalDiscount.add(discount);
            } else {
                if (discount.compareTo(highestPercentageDiscount) > 0) {
                    highestPercentageDiscount = discount;
                }
            }
        }
        return totalDiscount.add(highestPercentageDiscount);
    }
}
