package com.product.api.productstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.productstore.entity.UserEntity;
import com.product.api.productstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JwtAuthenticationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() throws Exception {
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    void testAuthentication_returnsOk() throws Exception {
        // Given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "chung");
        loginRequest.put("password", "password");

        // When
        ResultActions response = mockMvc.perform(post("/authenticate")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken", notNullValue()));
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    void testAuthentication_usingWrongPassword_throwsException() throws Exception {
        // Given
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "chung");
        loginRequest.put("password", "wrongPassword");

        // When
        ResultActions response = mockMvc.perform(post("/authenticate")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON));

        // Then
        response.andExpect(status().isUnauthorized());
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    void testRegister_returnsOk() throws Exception {
        // Given
        Map<String, String> registerUser = new HashMap<>();
        registerUser.put("username", "testUser");
        registerUser.put("password", "testPassword");
        registerUser.put("pseudonym", "testPseudonym");

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "testUser");
        loginRequest.put("password", "testPassword");

        // When
        ResultActions responseRegister = mockMvc.perform(post("/register")
                .content(objectMapper.writeValueAsString(registerUser))
                .contentType(MediaType.APPLICATION_JSON));

        ResultActions responseAuthenticate = mockMvc.perform(post("/authenticate")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON));

        Optional<UserEntity> userEntity = userRepository.findByUsername("testUser");

        // Then
        responseRegister.andExpect(status().isOk());
        responseAuthenticate.andExpect(status().isOk());

        assertTrue(userEntity.isPresent());
        assertEquals(userEntity.get().getUsername(), "testUser");
        assertEquals(userEntity.get().getPseudonym(), "testPseudonym");
    }
}
