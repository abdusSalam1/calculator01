package com.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
public class BillSummaryModel {

    private BigDecimal totalAmount;
    private BigDecimal discountApplied;
    private BigDecimal finalAmount;
    private String currency;
}
