package com.assignment.service;

import com.assignment.model.ExchangeRateModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final RestTemplate restTemplate;
    @Value("${exchange.api.base-url}")
    private String baseUrl;
    @Value("${exchange.api.key}")
    private String apiKey;

    @Override
    @Cacheable(value = "exchangeRates", key = "#fromCurrency + '-' + #toCurrency")
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .buildAndExpand(apiKey, fromCurrency)
                .toUriString();

        try {
            ExchangeRateModel response = restTemplate.getForObject(url, ExchangeRateModel.class);

            if (response == null || !"success".equals(response.getResult())) {
                throw new RuntimeException("Failed to fetch exchange rates from API");
            }

            BigDecimal targetRate = response.getConversionRates().get(toCurrency);
            if (targetRate == null) {
                throw new IllegalArgumentException("Target currency rate not available");
            }
            return targetRate;

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("An unexpected error occurred", ex);
        }
    }
}
