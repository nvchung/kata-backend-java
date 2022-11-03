package com.product.api.productstore.entity;

import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecifications {

    public static Specification<ProductEntity> productHasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get(ProductEntity_.ID), id);
    }
}
