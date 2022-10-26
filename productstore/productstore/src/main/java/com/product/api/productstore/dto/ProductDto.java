package com.product.api.productstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Product dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 4572045198136554735L;

    /*
     * Product id.
     */
    private Long id;

    /*
     * Product title.
     */
    private String title;

    /*
     * Product description.
     */
    private String description;

    /*
     * user own the product.
     */
    private UserDto owner;

    /*
     * Product image url.
     */
    private String productImageUrl;

    /*
     * Product price.
     */
    private BigDecimal price;

    /*
     * Product publish.
     */
    private boolean isPublish;

    public ProductDto(Long id, String title, String description, UserDto owner, String productImageUrl, BigDecimal price, boolean isPublish) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.isPublish = isPublish;
    }
}
