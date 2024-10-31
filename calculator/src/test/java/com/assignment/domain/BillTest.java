package com.assignment.domain;

import com.assignment.manager.DiscountManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BillTest {

    @Mock
    private DiscountManager discountManager;

    private Bill bill;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Product product1 = new Product("Product 1", new BigDecimal("50.00"),ProductCategory.ELECTRONICS);
        Product product2 = new Product("Product 2", new BigDecimal("30.00"), ProductCategory.ELECTRONICS);
        products = Arrays.asList(product1, product2);

        User testUser = new User("Test User", UserType.STANDARD, LocalDate.now());
        bill = new Bill(testUser, products, "USD");
    }

    @Test
    void shouldReturnBillSummaryWithCorrectValues() {
        when(discountManager.calculateTotalDiscount(bill)).thenReturn(new BigDecimal("15.00"));

        BigDecimal totalAmount = new BigDecimal("80.00");
        BigDecimal totalDiscount = new BigDecimal("15.00");
        BigDecimal finalAmount = totalAmount.subtract(totalDiscount);

        BillSummary billSummary = bill.createSummary(discountManager);

        assertEquals(totalAmount, billSummary.getTotalAmount());
        assertEquals(totalDiscount, billSummary.getDiscountApplied());
        assertEquals(finalAmount, billSummary.getFinalAmount());
        assertEquals("USD", billSummary.getCurrency());
    }
}
