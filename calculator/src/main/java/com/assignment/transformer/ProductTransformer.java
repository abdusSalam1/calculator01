package com.assignment.transformer;

import com.assignment.domain.Product;
import com.assignment.domain.ProductCategory;
import com.assignment.model.ProductModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductTransformer implements Transformer<ProductModel, Product> {
    @Override
    public Product toEntity(ProductModel model) {
        Objects.requireNonNull(model, "Product data cannot be null");
        return Product.builder()
                .name(model.getName())
                .category(toProductCategory(model.getCategory()))
                .price(model.getPrice())
                .build();
    }

    @Override
    public ProductModel toModel(Product product) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private ProductCategory toProductCategory(String category) {
        return StringUtils.isNotBlank(category) ? ProductCategory.valueOf(category.toUpperCase()) : null;
    }
}
