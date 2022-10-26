package com.product.api.productstore.service.impl;

import com.product.api.productstore.entity.ProductEntity;
import com.product.api.productstore.repository.ProductRepository;
import com.product.api.productstore.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Product service impl.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Find all products.
     *
     * @return product list
     */
    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    /**
     * Find product by id
     *
     * @param id product id
     * @return product
     */
    @Override
    public ProductEntity findById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        return product.orElse(null);
    }

    /**
     * Publish product.
     *
     * @param productEntity product entity
     * @return product published
     */
    @Override
    public ProductEntity publishProduct(ProductEntity productEntity) {
        productEntity.setPublish(true);
        return updateProduct(productEntity);
    }

    /**
     * Update product.
     *
     * @param productEntity product entity
     * @return product updated
     */
    @Override
    public ProductEntity updateProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    /**
     * Unpublish product.
     *
     * @param productEntity product entity
     * @param userName      owner username
     * @return product unpublished
     * @throws Exception exception
     */
    @Override
    public ProductEntity unPublishProduct(ProductEntity productEntity, String userName) throws Exception {
        if (StringUtils.equals(productEntity.getOwner().getUserName(), userName)) {
            productEntity.setPublish(false);
            return updateProduct(productEntity);
        } else {
            throw new Exception("Not owner!");
        }
    }
}
