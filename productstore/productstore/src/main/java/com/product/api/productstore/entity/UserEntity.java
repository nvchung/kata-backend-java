package com.product.api.productstore.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="USER")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Id Long id;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "PSEUDONYM")
    private String pseudonym;
    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<ProductEntity> productEntities;

    public UserEntity(Long id, String userName, String pseudonym, String password) {
        this.id = id;
        this.userName = userName;
        this.pseudonym = pseudonym;
        this.password = password;
    }
}
