package com.product.api.productstore;

import com.product.api.productstore.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ProductStoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql({ "classpath:schema.sql", "classpath:data.sql" })
    @Test
    public void testAllProducts()
    {
        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/products/all", String.class);

    }
}
