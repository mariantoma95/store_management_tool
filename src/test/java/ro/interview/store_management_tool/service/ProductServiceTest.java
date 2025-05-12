package ro.interview.store_management_tool.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ro.interview.store_management_tool.dto.ProductDto;
import ro.interview.store_management_tool.dto.ProductPriceDto;
import ro.interview.store_management_tool.dto.ProductStockDto;
import ro.interview.store_management_tool.exception.ProductAlreadyExistsException;
import ro.interview.store_management_tool.exception.ProductNotFoundException;
import ro.interview.store_management_tool.mapper.ProductMapper;
import ro.interview.store_management_tool.model.Product;
import ro.interview.store_management_tool.model.Stock;
import ro.interview.store_management_tool.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ro.interview.store_management_tool.dto.ProductType.ELECTRONICS;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    public static final String SKU = "9001";
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = setProduct(1L, "Product Test", 20);
        productDto = getProductDtoFromProduct(product);
    }

    @Test
    void testUpdatePriceSuccessfully() {
        ProductPriceDto priceDto = ProductPriceDto.builder().price(BigDecimal.valueOf(109.99)).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.mapProductToProductDtoMapper(product)).thenReturn(productDto);

        ProductDto result = productService.updatePrice(1L, priceDto);

        assertEquals(productDto, result);
        assertEquals(BigDecimal.valueOf(109.99), product.getPrice());
        verify(productRepository).save(product);
    }

    @Test
    void testUpdatePriceProductNotFoundException() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () ->
                productService.updatePrice(2L, ProductPriceDto.builder().price(BigDecimal.valueOf(99.99)).build()));
    }

    @Test
    void testGetAllProducts() {
        Pageable pageable = PageRequest.of(0, 4);
        var product2 = setProduct(2L, "Product Test 2", 10);
        var product3 = setProduct(2L, "Product Test 3", 15);

        when(productRepository.findAll(pageable)).thenReturn(List.of(product, product2, product3));
        when(productMapper.mapProductToProductDtoMapper(any())).thenReturn(productDto);

        List<ProductDto> list = productService.getAllProducts(0, 4);

        assertEquals(3, list.size());
        assertEquals(productDto, list.getFirst());
    }

    @Test
    void testUpdateQuantitySuccessfully() {
        ProductStockDto stockDto = ProductStockDto.builder().quantity(25).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.mapProductToProductDtoMapper(product)).thenReturn(productDto);

        ProductDto result = productService.updateQuantity(1L, stockDto);

        assertEquals(productDto, result);
        assertEquals(25, product.getStock().getQuantity());
        verify(productRepository).save(product);
    }

    @Test
    void testUpdateQuantityProductNotFoundException() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () ->
                productService.updateQuantity(2L, ProductStockDto.builder().quantity(25).build()));
    }

    @Test
    void getProductBySkuSuccessfully() {
        when(productRepository.findBySku(SKU)).thenReturn(Optional.of(product));
        when(productMapper.mapProductToProductDtoMapper(product)).thenReturn(productDto);

        ProductDto result = productService.getProductBySku(SKU);

        assertEquals(productDto, result);
    }

    @Test
    void getProductBySkuProductNotFoundException() {
        when(productRepository.findBySku(SKU)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductBySku(SKU));
    }

    @Test
    void getCreateProductSuccessfully() {
        when(productRepository.existsBySku(SKU)).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.mapProductToProductDtoMapper(product)).thenReturn(productDto);

        ProductDto result = productService.createProduct(productDto);

        assertEquals(productDto, result);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getCreateProductWithDefaultQuantitySuccessfully() {
        var productWithAbsentQuantity = setProduct(3L, "Test Product Absent Quantity", 0);
        var productWithAbsentQuantityDto = getProductDtoFromProductWithAbsentQuantity(productWithAbsentQuantity);
        var mappedProductDto = getProductDtoFromProduct(productWithAbsentQuantity);

        when(productRepository.existsBySku(SKU)).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(productWithAbsentQuantity);
        when(productMapper.mapProductToProductDtoMapper(productWithAbsentQuantity)).thenReturn(mappedProductDto);

        ProductDto result = productService.createProduct(productWithAbsentQuantityDto);

        assertEquals(0, result.quantity());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getCreateProductSkuAlreadyExistsException() {
        when(productRepository.existsBySku(SKU)).thenReturn(true);
        assertThrows(ProductAlreadyExistsException.class, () -> productService.createProduct(productDto));
    }

    private Product setProduct(Long id, String name, Integer quantity) {
        var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription("Product Description");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setType(ELECTRONICS);
        product.setSku(SKU);

        var stock = new Stock();
        stock.setId(1L);
        stock.setSku(SKU);
        stock.setQuantity(quantity);

        stock.setProduct(product);
        product.setStock(stock);

        return product;
    }

    private ProductDto getProductDtoFromProduct(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getType(),
                product.getSku(),
                product.getStock().getQuantity()
        );
    }

    private ProductDto getProductDtoFromProductWithAbsentQuantity(Product product) {
        return new ProductDto(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getType(),
                product.getSku(),
                null
        );
    }
}
