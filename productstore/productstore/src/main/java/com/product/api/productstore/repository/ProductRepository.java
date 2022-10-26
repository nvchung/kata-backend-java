package com.product.api.productstore.repository;

import com.product.api.productstore.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Product repository.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
