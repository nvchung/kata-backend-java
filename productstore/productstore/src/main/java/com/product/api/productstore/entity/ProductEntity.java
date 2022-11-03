package com.product.api.productstore.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Product entity.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {
    /*
     * Product id.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Id Long id;

    /*
     * Product title.
     */
    @Column(name = "TITLE")
    private String title;

    /*
     * Product description.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /*
     * Product owner.
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity owner;

    /*
     * Product image url.
     */
    @Column(name = "PRODUCT_IMAGE_URL")
    private String productImageUrl;

    /*
     * Product price.
     */
    @Column(name = "PRICE")
    private BigDecimal price;
    /*
     * Product publish.
     */
    @Column(name = "PUBLIC")
    private boolean isPublish;

    public ProductEntity(Long id, String title, String description, UserEntity owner, String productImageUrl, BigDecimal price, boolean isPublish) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.isPublish = isPublish;
    }
}
