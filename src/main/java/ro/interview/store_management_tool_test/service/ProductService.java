package ro.interview.store_management_tool_test.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.dto.ProductPriceDto;
import ro.interview.store_management_tool_test.dto.ProductStockDto;
import ro.interview.store_management_tool_test.exception.ProductAlreadyExistsException;
import ro.interview.store_management_tool_test.exception.ProductNotFoundException;
import ro.interview.store_management_tool_test.mapper.ProductMapper;
import ro.interview.store_management_tool_test.model.Product;
import ro.interview.store_management_tool_test.model.Stock;
import ro.interview.store_management_tool_test.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto updatePrice(Long id, ProductPriceDto productPriceDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setPrice(productPriceDto.getPrice());
        productRepository.save(product);

        return productMapper.mapProductToProductDtoMapper(product);
    }

    public List<ProductDto> getAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products = productRepository.findAll(pageable);

        return products.stream().map(productMapper::mapProductToProductDtoMapper).toList();
    }

    @Transactional
    public ProductDto updateQuantity(Long id, ProductStockDto productStockDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.getStock().setQuantity(productStockDto.getQuantity());
        productRepository.save(product);

        return productMapper.mapProductToProductDtoMapper(product);
    }

    public ProductDto getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));

        return productMapper.mapProductToProductDtoMapper(product);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        if (productRepository.existsBySku(productDto.sku())) {
            throw new ProductAlreadyExistsException(productDto.sku());
        }

        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setType(productDto.type());
        product.setSku(productDto.sku());

        Stock stock = new Stock();
        stock.setSku(productDto.sku());
        stock.setQuantity(productDto.quantity() == null ? 0 : productDto.quantity());

        stock.setProduct(product);
        product.setStock(stock);

        Product savedProduct = productRepository.save(product);

        return productMapper.mapProductToProductDtoMapper(savedProduct);
    }
}
