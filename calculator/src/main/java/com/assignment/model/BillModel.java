package com.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class BillModel {

    private String userName;
    private String userType;
    private LocalDate userAffiliationDate;
    private List<ProductModel> products;
    private String originalCurrency;
    private String targetCurrency;

}
