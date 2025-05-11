package ro.interview.store_management_tool_test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.dto.ProductPriceDto;
import ro.interview.store_management_tool_test.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/v1")
public class ProductsController {

    private final ProductService productService;

    @PutMapping("/{id}/price")
    public ResponseEntity<ProductDto> updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody ProductPriceDto productPriceDto) {

        return ResponseEntity.ok(productService.updatePrice(id, productPriceDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer size) {

        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }
}
