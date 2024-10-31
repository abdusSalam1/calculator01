package com.assignment.transformer;

import com.assignment.domain.Bill;
import com.assignment.domain.Product;
import com.assignment.domain.User;
import com.assignment.domain.UserType;
import com.assignment.model.BillModel;
import com.assignment.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BillTransformer implements Transformer<BillModel, Bill> {

    private final Transformer<ProductModel, Product> productTransformer;

    @Override
    public Bill toEntity(BillModel model) {
        Objects.requireNonNull(model, "Bill data cannot be null");
        User user = toUser(model);
        List<Product> products = productTransformer.toEntities(model.getProducts());
        return Bill.builder()
                .user(user)
                .products(products)
                .currency(model.getOriginalCurrency())
                .build();
    }

    @Override
    public BillModel toModel(Bill bill) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private User toUser(BillModel model) {
        Objects.requireNonNull(model.getUserName(), "User name cannot be null");
        Objects.requireNonNull(model.getUserType(), "User type cannot be null");
        Objects.requireNonNull(model.getUserAffiliationDate(), "User affiliation date cannot be null");
        return User.builder()
                .name(model.getUserName())
                .type(UserType.valueOf(model.getUserType().toUpperCase()))
                .affiliationDate(model.getUserAffiliationDate())
                .build();
    }
}
