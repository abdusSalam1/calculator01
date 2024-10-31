package com.assignment.calculator;

import com.assignment.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CustomerTenureDiscountCalculatorTest {

    @Value("${discount.customerTenure}")
    private BigDecimal discountPercentage;

    @Autowired
    private CustomerTenureDiscountCalculator discountCalculator;

    @Test
    void calculate_shouldReturnDiscount_WhenUserIsOldCustomer() {
        User oldCustomer = new User("John Doe", UserType.STANDARD, LocalDate.now().minusYears(5));
        Product product1 = new Product("Product 1", BigDecimal.valueOf(100), ProductCategory.ELECTRONICS);
        Product product2 = new Product("Product 2", BigDecimal.valueOf(200), ProductCategory.ELECTRONICS);
        Bill bill = new Bill(oldCustomer, Arrays.asList(product1, product2), "USD");

        BigDecimal discount = discountCalculator.calculate(bill);

        BigDecimal expectedDiscount = BigDecimal.valueOf(300).multiply(discountPercentage);
        assertEquals(expectedDiscount, discount);
    }

    @Test
    void calculate_shouldReturnZeroDiscount_WhenUserIsNotOldCustomer() {
        User newCustomer = new User("Jane Doe", UserType.STANDARD, LocalDate.now());
        Product product1 = new Product("Product 1", BigDecimal.valueOf(100), ProductCategory.ELECTRONICS);
        Bill bill = new Bill(newCustomer, List.of(product1), "USD");

        BigDecimal discount = discountCalculator.calculate(bill);

        assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    void calculate_shouldIgnoreGroceryProducts_WhenCalculatingDiscount() {
        User oldCustomer = new User("Alice", UserType.STANDARD, LocalDate.now().minusYears(3));
        Product groceryProduct = new Product("Grocery Product", BigDecimal.valueOf(50), ProductCategory.GROCERY);
        Product nonGroceryProduct = new Product("Non-Grocery Product", BigDecimal.valueOf(150), ProductCategory.ELECTRONICS);
        Bill bill = new Bill(oldCustomer, Arrays.asList(groceryProduct, nonGroceryProduct), "USD");

        BigDecimal discount = discountCalculator.calculate(bill);

        BigDecimal expectedDiscount = BigDecimal.valueOf(150).multiply(discountPercentage);
        assertEquals(expectedDiscount, discount);
    }
}
