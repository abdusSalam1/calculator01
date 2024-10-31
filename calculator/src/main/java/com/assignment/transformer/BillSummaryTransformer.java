package com.assignment.transformer;

import com.assignment.domain.BillSummary;
import com.assignment.model.BillSummaryModel;
import org.springframework.stereotype.Component;

@Component
public class BillSummaryTransformer implements Transformer<BillSummaryModel, BillSummary> {
    @Override
    public BillSummary toEntity(BillSummaryModel billSummaryModel) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public BillSummaryModel toModel(BillSummary billSummary) {
        return BillSummaryModel.builder()
                .totalAmount(billSummary.getTotalAmount())
                .finalAmount(billSummary.getFinalAmount())
                .discountApplied(billSummary.getDiscountApplied())
                .currency(billSummary.getCurrency())
                .build();
    }
}
