package ro.interview.store_management_tool.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.interview.store_management_tool.dto.ProductDto;
import ro.interview.store_management_tool.dto.ProductPriceDto;
import ro.interview.store_management_tool.dto.ProductStockDto;
import ro.interview.store_management_tool.service.ProductService;

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

    @PutMapping("/{id}/quantity")
    public ResponseEntity<ProductDto> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody ProductStockDto productStockDto) {

        return ResponseEntity.ok(productService.updateQuantity(id, productStockDto));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productService.getProductBySku(sku));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }
}
