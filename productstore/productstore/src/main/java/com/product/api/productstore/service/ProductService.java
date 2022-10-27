package com.product.api.productstore.service;

import com.product.api.productstore.entity.ProductEntity;

import java.util.List;

/**
 * Product service interface.
 */
public interface ProductService {

    List<ProductEntity> findAll();

    ProductEntity findById(Long id);

    ProductEntity publishProduct(ProductEntity productEntity) throws Exception;

    ProductEntity updateProduct(ProductEntity productEntity) throws Exception;

    ProductEntity unPublishProduct(ProductEntity productEntity, String userName) throws Exception;

}
