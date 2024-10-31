package com.assignment.delegate;

import com.assignment.domain.Bill;
import com.assignment.domain.BillSummary;
import com.assignment.manager.DiscountManager;
import com.assignment.model.BillModel;
import com.assignment.model.BillSummaryModel;
import com.assignment.service.CurrencyExchangeService;
import com.assignment.transformer.BillSummaryTransformer;
import com.assignment.transformer.BillTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class BillControllerDelegate {

    private final BillTransformer billTransformer;
    private final BillSummaryTransformer billSummaryTransformer;
    private final DiscountManager discountManager;
    private final CurrencyExchangeService currencyExchangeService;

    public BillSummaryModel calculateBill(BillModel billModel) {
        Bill bill = billTransformer.toEntity(billModel);
        BillSummary summary = bill.createSummary(discountManager);
        BillSummary summaryInTargetCurrency = createSummaryByCurrency(billModel.getTargetCurrency(), summary);
        return billSummaryTransformer.toModel(summaryInTargetCurrency);
    }

    private BillSummary createSummaryByCurrency(String targetCurrency, BillSummary summary){
        String originalCurrency = summary.getCurrency();
        BigDecimal exchangeRate = currencyExchangeService.getExchangeRate(originalCurrency, targetCurrency);
        return BillSummary.builder()
                .totalAmount(summary.getTotalAmount().multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP))
                .discountApplied(summary.getDiscountApplied().multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP))
                .finalAmount(summary.getFinalAmount().multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP))
                .currency(targetCurrency)
                .build();
    }
}
