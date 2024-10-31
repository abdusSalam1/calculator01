package com.assignment.transformer;

import com.assignment.domain.Bill;
import com.assignment.domain.Product;
import com.assignment.domain.ProductCategory;
import com.assignment.domain.UserType;
import com.assignment.model.BillModel;
import com.assignment.model.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BillTransformerTest {

    @Mock
    private Transformer<ProductModel, Product> productTransformer;

    @InjectMocks
    private BillTransformer billTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldTransformBillModelToBillWithProducts() {
        LocalDate date = LocalDate.of(2024, 1, 1);

        ProductModel productModel = new ProductModel();
        productModel.setName("Test Product");
        productModel.setCategory("ELECTRONICS");
        productModel.setPrice(BigDecimal.valueOf(200.00));

        BillModel model = createBillModel("John", "EMPLOYEE", date, Collections.singletonList(productModel));

        Product product = Product.builder()
                .name("Test Product")
                .category(ProductCategory.ELECTRONICS)
                .price(BigDecimal.valueOf(200.00))
                .build();

        when(productTransformer.toEntities(model.getProducts())).thenReturn(Collections.singletonList(product));

        Bill bill = billTransformer.toEntity(model);

        assertNotNull(bill);
        assertEquals("John", bill.getUser().getName());
        assertEquals(UserType.EMPLOYEE, bill.getUser().getType());
        assertEquals(date, bill.getUser().getAffiliationDate());
        assertEquals(1, bill.getProducts().size());

        Product transformedProduct = bill.getProducts().get(0);
        assertEquals("Test Product", transformedProduct.getName());
        assertEquals(ProductCategory.ELECTRONICS, transformedProduct.getCategory());
        assertEquals(BigDecimal.valueOf(200.00), transformedProduct.getPrice());
    }


    @Test
    void shouldThrowExceptionWhenModelIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> billTransformer.toEntity(null));
        assertEquals("Bill data cannot be null", exception.getMessage());
    }

    @Test
    void shouldHandleNullUserNameInUserFields() {
        BillModel model = createBillModel(null, null, null, Collections.emptyList());
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> billTransformer.toEntity(model));
        assertEquals("User name cannot be null", exception.getMessage());
    }

    @Test
    void shouldHandleNullUserTypeInUserFields() {
        BillModel model = createBillModel("Dummy", null, null, Collections.emptyList());
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> billTransformer.toEntity(model));
        assertEquals("User type cannot be null", exception.getMessage());
    }

    @Test
    void shouldHandleNullUserAffiliationDateInUserFields() {
        BillModel model = createBillModel("Dummy", "EMPLOYEE", null, Collections.emptyList());
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> billTransformer.toEntity(model));
        assertEquals("User affiliation date cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserTypeIsInvalid() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        BillModel model = createBillModel("Denny", "INVALID_TYPE", date, Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> billTransformer.toEntity(model));
    }

    @Test
    void shouldHandleNullProductsList() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        BillModel model = createBillModel("Denny", "STANDARD", date, null);

        when(productTransformer.toEntities(null)).thenReturn(Collections.emptyList());

        Bill bill = billTransformer.toEntity(model);

        assertNotNull(bill);
        assertEquals("Denny", bill.getUser().getName());
        assertEquals(UserType.STANDARD, bill.getUser().getType());
        assertEquals(LocalDate.of(2024, 1, 1), bill.getUser().getAffiliationDate());
        assertTrue(bill.getProducts().isEmpty());
    }

    private BillModel createBillModel(String userName, String userType, LocalDate date, List<ProductModel> products) {
        BillModel model = new BillModel();
        model.setUserName(userName);
        model.setUserType(userType);
        model.setUserAffiliationDate(date);
        model.setProducts(products);
        return model;
    }

}