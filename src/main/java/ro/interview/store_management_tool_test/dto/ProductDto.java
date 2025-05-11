package ro.interview.store_management_tool_test.dto;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        String description,
        BigDecimal price,
        ProductType type,
        String sku,
        Integer quantity) {
}
