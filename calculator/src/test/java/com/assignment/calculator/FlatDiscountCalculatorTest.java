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
class FlatDiscountCalculatorTest {

    @Autowired
    private FlatDiscountCalculator discountCalculator;

    @Test
    void shouldApplyFlatDiscount() {
        Bill bill = createBill(Arrays.asList(
                new Product("Laptop", new BigDecimal("1000.00"), ELECTRONICS),
                new Product("Book", new BigDecimal("200.00"), OTHER),
                new Product("Apple", new BigDecimal("50.00"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(new BigDecimal("60.00"), discount.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void shouldReturnZeroDiscountForEmptyBill() {
        Bill bill = createBill(List.of());

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discount);
    }

    @Test
    void shouldReturnZeroDiscountForLessThan100() {
        Bill bill = createBill(List.of(
                new Product("Apple", new BigDecimal("50.00"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discount);
    }

    @Test
    void shouldHandleMultipleProductsCorrectly() {
        Bill bill = createBill(Arrays.asList(
                new Product("Laptop", new BigDecimal("300.00"), ELECTRONICS),
                new Product("Book", new BigDecimal("150.00"), OTHER),
                new Product("Grocery Item", new BigDecimal("30.00"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(new BigDecimal("20.00"), discount.setScale(2, RoundingMode.HALF_UP));
    }

    private Bill createBill(List<Product> products) {
        User user = User.builder()
                .name("Johny")
                .type(UserType.STANDARD)
                .affiliationDate(LocalDate.of(2023, 01, 01))
                .build();
        return new Bill(user, products, "USD");
    }
}
