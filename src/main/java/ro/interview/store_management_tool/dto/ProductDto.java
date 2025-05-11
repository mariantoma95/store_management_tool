package ro.interview.store_management_tool.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(
        @NotBlank String name,
        String description,
        @NotNull @DecimalMin(value = "0.0") BigDecimal price,
        @NotNull ProductType type,
        @NotNull String sku,
        @Min(0) Integer quantity) {
}
