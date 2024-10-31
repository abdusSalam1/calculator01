package com.assignment.controller;

import com.assignment.delegate.BillControllerDelegate;
import com.assignment.model.BillModel;
import com.assignment.model.BillSummaryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillControllerDelegate billControllerDelegate;

    @PostMapping(value = "/calculate")
    public ResponseEntity<BillSummaryModel> calculateBill(@RequestBody BillModel billModel) {
        BillSummaryModel summary = billControllerDelegate.calculateBill(billModel);
        return ResponseEntity.ok(summary);
    }
}
