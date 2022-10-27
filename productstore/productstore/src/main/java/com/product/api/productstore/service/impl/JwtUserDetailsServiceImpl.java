package com.product.api.productstore.service.impl;

import com.product.api.productstore.dto.UserDto;
import com.product.api.productstore.entity.UserEntity;
import com.product.api.productstore.repository.UserRepository;
import com.product.api.productstore.service.JwtUserDetailsService;
import com.product.api.productstore.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Jwt user details service impl.
 */
@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user by username.
     *
     * @param username username
     * @return user
     * @throws UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    /**
     * Save user.
     *
     * @param user user
     * @return user saved
     */
    public UserEntity save(UserDto user) {
        UserEntity newUser = ModelMapperUtil.map(user, UserEntity.class);
        return userRepository.save(newUser);
    }

}
