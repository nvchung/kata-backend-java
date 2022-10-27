package com.product.api.productstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * User dto.
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = 6078520215036749655L;

    /*
     * user id.
     */
    private Long id;

    /*
     * username.
     */
    private String username;

    /*
     * user pseudonym.
     */
    private String pseudonym;

    /**
     * user password
     */
    private String password;

    public UserDto(Long id, String userName, String pseudonym, String password) {
        this.id = id;
        this.username = userName;
        this.pseudonym = pseudonym;
        this.password = password;
    }
}
