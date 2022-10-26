package com.product.api.productstore.dto;

import lombok.Getter;

import java.io.Serializable;

/**
 * Jwt response.
 */
@Getter
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 2578190302957028390L;

    /**
     * Authentication jwt token.
     */
    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
