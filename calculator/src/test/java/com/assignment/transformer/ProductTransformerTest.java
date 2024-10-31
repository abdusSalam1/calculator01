package com.assignment.transformer;

import com.assignment.domain.Product;
import com.assignment.domain.ProductCategory;
import com.assignment.model.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTransformerTest {
    private ProductTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new ProductTransformer();
    }

    @Test
    void shouldTransformProductModelToProduct() {
        ProductModel model = createProduct("Test Product", "ELECTRONICS", BigDecimal.valueOf(100.0));

        Product product = transformer.toEntity(model);

        assertEquals("Test Product", product.getName());
        assertEquals(ProductCategory.ELECTRONICS, product.getCategory());
        assertEquals(0, BigDecimal.valueOf(100.0).compareTo(product.getPrice()));
    }

    @Test
    void shouldThrowExceptionWhenModelIsNull() {
        assertThrows(NullPointerException.class, () -> transformer.toEntity(null),"Product data cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenCategoryIsInvalid() {
        ProductModel model = createProduct("Test Product", "INVALID_CATEGORY", BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class, () -> transformer.toEntity(model));
    }

    @Test
    void shouldHandleNullValuesInModelFields() {
        ProductModel model = createProduct(null, null, null);

        Product product = transformer.toEntity(model);

        assertNull(product.getName());
        assertNull(product.getCategory());
        assertNull(product.getPrice());
    }

    private ProductModel createProduct(String name, String category, BigDecimal price) {
        ProductModel model = new ProductModel();
        model.setName(name);
        model.setCategory(category);
        model.setPrice(price);
        return model;
    }
}