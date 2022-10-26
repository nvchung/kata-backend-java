package com.product.api.productstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = -170604442189889511L;

    /*
     * Authentication username
     */
    private String username;

    /*
     * Authentication password
     */
    private String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
