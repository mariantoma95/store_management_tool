package ro.interview.store_management_tool_test.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;
    private ProductType type;
    private String sku;
}
