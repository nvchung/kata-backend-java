package com.product.api.productstore.service;

import com.product.api.productstore.entity.ProductEntity;

import java.util.List;

/**
 * Product service interface.
 */
public interface ProductService {

    List<ProductEntity> findAll();

    ProductEntity findById(Long id);

    ProductEntity publishProduct(ProductEntity productEntity);

    ProductEntity updateProduct(ProductEntity productEntity);

    ProductEntity unPublishProduct(ProductEntity productEntity, String userName) throws Exception;

}
