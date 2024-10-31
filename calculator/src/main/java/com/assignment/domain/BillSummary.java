package com.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
public class BillSummary {

    private final BigDecimal totalAmount;
    private final BigDecimal discountApplied;
    private final BigDecimal finalAmount;
    private final String currency;
}
