package com.assignment.transformer;

import java.util.List;
import java.util.stream.Collectors;

public interface Transformer<M, E> {

    E toEntity(M m);

    M toModel(E e);

    default List<E> toEntities(List<M> m) {
        return m.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
