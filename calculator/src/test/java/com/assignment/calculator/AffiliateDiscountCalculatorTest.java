package com.assignment.calculator;

import com.assignment.domain.Bill;
import com.assignment.domain.Product;
import com.assignment.domain.User;
import com.assignment.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.assignment.domain.ProductCategory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AffiliateDiscountCalculatorTest {

    @Autowired
    private AffiliateDiscountCalculator discountCalculator;

    @Test
    void shouldApplyAffiliateDiscountForNonGroceryProducts() {
        Bill bill = createBill(true, Arrays.asList(
                new Product("Laptop", new BigDecimal("1000.00"), ELECTRONICS),
                new Product("Book", new BigDecimal("200.00"), OTHER),
                new Product("Apple", new BigDecimal("50.00"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(new BigDecimal("120.00"), discount.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    void shouldReturnZeroDiscountForAffiliateWithOnlyGroceryProducts() {
        Bill bill = createBill(true, Arrays.asList(
                new Product("Apple", new BigDecimal("50.00"), GROCERY),
                new Product("Banana", new BigDecimal("30.00"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discount);
    }

    @Test
    void shouldReturnZeroDiscountForNonAffiliateUser() {
        Bill bill = createBill(false, Arrays.asList(
                new Product("Laptop", new BigDecimal("1000.00"), ELECTRONICS),
                new Product("Book", new BigDecimal("200.00"), OTHER)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discount);
    }

    @Test
    void shouldApplyDiscountCorrectlyWithMultipleProducts() {
        Bill bill = createBill(true, Arrays.asList(
                new Product("Laptop", new BigDecimal("300.00"), ELECTRONICS),
                new Product("Chair", new BigDecimal("150.00"), OTHER),
                new Product("Grocery Item", new BigDecimal("30.00"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(new BigDecimal("45.00"), discount.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    private Bill createBill(boolean isAffiliate, List<Product> products) {
        User user = User.builder()
                .name("Johny")
                .type(isAffiliate ? UserType.AFFILIATE : UserType.STANDARD)
                .affiliationDate(LocalDate.of(2023, 01, 01))
                .build();
        return new Bill(user, products, "USD");
    }
}
