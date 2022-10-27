package com.product.api.productstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.productstore.dto.ProductDto;
import com.product.api.productstore.entity.ProductEntity;
import com.product.api.productstore.repository.ProductRepository;
import com.product.api.productstore.util.ModelMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("username", "chung");
        params.put("password", "password");

        ResultActions response = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)));

        String tokenResponse = response.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        token = jsonParser.parseMap(tokenResponse).get("jwtToken").toString();
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    public void testAllProducts() throws Exception {

        // Given
        List<ProductEntity> productEntityList = productRepository.findAll();

        // When
        ResultActions response = mockMvc.perform(get("/products/all"));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(productEntityList.size())));
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    public void testUpdateProduct_Auth_returnsOk() throws Exception {

        //Given
        Long productId = 1L;
        ProductEntity entity = productRepository.findById(productId).get();
        entity.setDescription("updated_desc");
        entity.setTitle("updated_title");
        ProductDto updateProduct = ModelMapperUtil.map(entity, ProductDto.class);

        // When
        ResultActions response = mockMvc.perform(put("/products/update")
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProduct)));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.description", is(updateProduct.getDescription())))
                .andExpect(jsonPath("$.title", is(updateProduct.getTitle())));
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    public void testUpdateProduct_noAuth_returnsUnauthorized() throws Exception {

        // Given
        Long productId = 1L;
        ProductEntity entity = productRepository.findById(productId).get();
        entity.setDescription("updated_desc");
        entity.setTitle("updated_title");
        ProductDto updateProduct = ModelMapperUtil.map(entity, ProductDto.class);

        // When
        ResultActions response = mockMvc.perform(put("/products/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProduct)));

        // Then
        response.andExpect(status().isUnauthorized());
    }

    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    @Test
    public void testPublishProduct_Auth_returnsOk() throws Exception {

        //Given
        Long productId = 1L;
        ProductEntity entity = productRepository.findById(productId).get();
        entity.setDescription("updated_desc");
        entity.setTitle("updated_title");
        ProductDto updateProduct = ModelMapperUtil.map(entity, ProductDto.class);

        // When
        ResultActions response = mockMvc.perform(post("/products/publish")
                .header(AUTHORIZATION, BEARER + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProduct)));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.description", is(updateProduct.getDescription())))
                .andExpect(jsonPath("$.title", is(updateProduct.getTitle())));
    }
}
