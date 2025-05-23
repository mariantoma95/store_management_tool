package ro.interview.store_management_tool.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool.dto.ProductDto;
import ro.interview.store_management_tool.dto.ProductPriceDto;
import ro.interview.store_management_tool.dto.ProductStockDto;
import ro.interview.store_management_tool.exception.ProductAlreadyExistsException;
import ro.interview.store_management_tool.exception.ProductNotFoundException;
import ro.interview.store_management_tool.mapper.ProductMapper;
import ro.interview.store_management_tool.model.Product;
import ro.interview.store_management_tool.model.Stock;
import ro.interview.store_management_tool.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @CacheEvict(value = "productBySku", key = "#result.sku")
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
    @CacheEvict(value = "productBySku", key = "#result.sku")
    public ProductDto updateQuantity(Long id, ProductStockDto productStockDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.getStock().setQuantity(productStockDto.getQuantity());
        productRepository.save(product);

        return productMapper.mapProductToProductDtoMapper(product);
    }

    @Cacheable(value = "productBySku", key = "#sku")
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
