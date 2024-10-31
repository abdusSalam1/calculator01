package com.assignment.calculator;

import com.assignment.domain.Bill;

import java.math.BigDecimal;

public interface DiscountCalculator {
    BigDecimal calculate(Bill bill);
}
