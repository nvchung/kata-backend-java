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
    private String username;
    @Column(name = "PSEUDONYM")
    private String pseudonym;
    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<ProductEntity> productEntities;

    public UserEntity(Long id, String username, String pseudonym, String password) {
        this.id = id;
        this.username = username;
        this.pseudonym = pseudonym;
        this.password = password;
    }
}
