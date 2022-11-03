package com.product.api.productstore.service.impl;

import com.product.api.productstore.entity.ProductEntity;
import com.product.api.productstore.entity.ProductSpecifications;
import com.product.api.productstore.entity.UserEntity;
import com.product.api.productstore.repository.ProductRepository;
import com.product.api.productstore.repository.UserRepository;
import com.product.api.productstore.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    @Autowired
    private UserRepository userRepository;

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
    public ProductEntity findById(final Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) throws Exception {
        Optional<UserEntity> user =  userRepository.findById(productEntity.getOwner().getId());
        if(user.isPresent()) {
            productEntity.setOwner(user.get());
            return productRepository.save(productEntity);
        } else {
            throw new Exception("Owner not found!");
        }
    }

    /**
     * Using specification query jpa.
     * @param entity e
     * @return product
     */
    public ProductEntity findBySpecification(final ProductEntity entity) {
        Specification<ProductEntity> specification = Specification.where(ProductSpecifications.productHasId(entity.getId()));
        List<ProductEntity> productEntities = productRepository.findAll(specification);
        return productEntities.get(0);
    }



    /**
     * Publish product.
     *
     * @param productEntity product entity
     * @return product published
     */
    @Override
    public ProductEntity publishProduct(final ProductEntity productEntity) throws Exception {
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
    public ProductEntity updateProduct(final ProductEntity productEntity) throws Exception {
        ProductEntity product = findById(productEntity.getId());
        if (product != null) {
            return productRepository.save(productEntity);
        } else {
            throw new Exception("Not found!");
        }
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
    public ProductEntity unPublishProduct(final ProductEntity productEntity, final String userName) throws Exception {
        if (StringUtils.equals(productEntity.getOwner().getUsername(), userName)) {
            productEntity.setPublish(false);
            return updateProduct(productEntity);
        } else {
            throw new Exception("Not owner!");
        }
    }
}
