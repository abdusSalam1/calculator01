package com.assignment.service;

import java.math.BigDecimal;

public interface CurrencyExchangeService {
    BigDecimal getExchangeRate(String fromCurrency, String toCurrency);
}