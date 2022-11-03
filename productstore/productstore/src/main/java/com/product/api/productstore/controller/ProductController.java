package com.product.api.productstore.controller;

import com.product.api.productstore.dto.ProductDto;
import com.product.api.productstore.entity.ProductEntity;
import com.product.api.productstore.service.ProductService;
import com.product.api.productstore.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Product controller.
 */
@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Get all products.
     *
     * @return product list
     */
    @GetMapping(path = "/all")
    public List<ProductDto> findAll() {
        List<ProductEntity> productEntities = productService.findAll();
        return ModelMapperUtil.mapAll(productEntities, ProductDto.class);
    }

    /**
     * Add product.
     *
     * @param productDto product dto
     * @return product added
     */
    @PostMapping(path = "/add")
    public ProductDto add(@Valid @RequestBody ProductDto productDto) throws Exception {
        ProductEntity productEntity = ModelMapperUtil.map(productDto, ProductEntity.class);
        return ModelMapperUtil.map(productService.save(productEntity), ProductDto.class);
    }

    /**
     * Update product.
     *
     * @param product product dto
     * @return product updated
     */
    @PutMapping(path = "/update")
    ProductDto update(@RequestBody ProductDto product) throws Exception {
        ProductEntity productEntity = ModelMapperUtil.map(product, ProductEntity.class);
        return ModelMapperUtil.map(productService.updateProduct(productEntity), ProductDto.class);
    }

    /**
     * UnPublic product.
     * @param id product id
     * @param authentication authentication user information
     * @return product un published
     * @throws Exception exception
     */
    @PostMapping(path = "/unpublish/{id}")
    public ProductDto unPublishProduct(@PathVariable("id") Long id, Authentication authentication) throws Exception {
        ProductEntity productEntity = productService.findById(id);
        return ModelMapperUtil.map(productService.unPublishProduct(productEntity, authentication.getName()), ProductDto.class);
    }

    /**
     * Publish product.
     *
     * @param id product id
     * @return product published
     */
    @PostMapping(path = "/publish/{id}")
    public ProductDto publicProduct(@PathVariable("id") Long id) throws Exception {
        ProductEntity productEntity = productService.findById(id);
        return ModelMapperUtil.map(productService.publishProduct(productEntity), ProductDto.class);
    }

    /**
     * Exception handler.
     *
     * @param exception exception
     * @return exception response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleNoSuchElementFoundException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
