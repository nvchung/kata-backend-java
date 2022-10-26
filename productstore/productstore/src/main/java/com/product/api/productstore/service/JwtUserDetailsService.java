package com.product.api.productstore.service;

import com.product.api.productstore.dto.UserDto;
import com.product.api.productstore.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Jwt UserDetails service interface.
 */
public interface JwtUserDetailsService extends UserDetailsService {
    UserEntity save(UserDto user);
}
