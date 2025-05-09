package ro.interview.store_management_tool_test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.dto.ProductPriceDto;
import ro.interview.store_management_tool_test.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/v1")
public class ProductsController {

    private final ProductService productService;

    @PutMapping("/{id}/price")
    public ResponseEntity<ProductDto> updatePrice(@PathVariable Long id, @Valid @RequestBody ProductPriceDto productPriceDto) {
        return ResponseEntity.ok(productService.updatePrice(id, productPriceDto));
    }
}
