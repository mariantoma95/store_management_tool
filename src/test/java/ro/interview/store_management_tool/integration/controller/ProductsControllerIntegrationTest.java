package ro.interview.store_management_tool.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ro.interview.store_management_tool.dto.ProductDto;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.interview.store_management_tool.dto.ProductType.ELECTRONICS;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductsControllerIntegrationTest {

    public static final String SKU = "9101";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateProduct() throws Exception {
        ProductDto productDto = new ProductDto(
                "Product Integration",
                "Product Desc Integration",
                BigDecimal.valueOf(49.99),
                ELECTRONICS,
                SKU,
                10
        );

        mockMvc.perform(post("/products/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sku").value(SKU))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.name").value("Product Integration"));
    }
}
