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
class EmployeeDiscountCalculatorTest {

    @Autowired
    private EmployeeDiscountCalculator discountCalculator;

    @Test
    void shouldApplyDiscountForEmployeeWithNonGroceryProducts() {
        Bill bill = createBill(true, Arrays.asList(
                new Product("Laptop", new BigDecimal("1000"), ELECTRONICS),
                new Product("Book", new BigDecimal("200"), OTHER),
                new Product("Apple", new BigDecimal("50"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(new BigDecimal("360.00"), discount);
    }

    @Test
    void calculate_shouldReturnZeroForNonEmployee() {
        Bill bill = createBill(false, Arrays.asList(
                new Product("Laptop", new BigDecimal("1000"), ELECTRONICS),
                new Product("Book", new BigDecimal("200"), OTHER)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discount);
    }

    @Test
    void calculate_shouldReturnZeroForEmployeeWithOnlyGroceryProducts() {
        Bill bill = createBill(true, Arrays.asList(
                new Product("Apple", new BigDecimal("50"), GROCERY),
                new Product("Banana", new BigDecimal("30"), GROCERY)
        ));

        BigDecimal discount = discountCalculator.calculate(bill);

        assertNotNull(discount, "Discount should not be null");
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), discount);
    }

    private Bill createBill(boolean isEmployee, List<Product> products) {
        User user = User.builder()
                .name("Johny")
                .type(isEmployee ? UserType.EMPLOYEE : UserType.STANDARD)
                .affiliationDate(LocalDate.of(2023, 01, 01))
                .build();
        return new Bill(user, products, "USD");
    }
}
