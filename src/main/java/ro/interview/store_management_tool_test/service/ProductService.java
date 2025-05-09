package ro.interview.store_management_tool_test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.dto.ProductPriceDto;
import ro.interview.store_management_tool_test.exception.ProductNotFoundException;
import ro.interview.store_management_tool_test.mapper.ProductMapper;
import ro.interview.store_management_tool_test.model.Product;
import ro.interview.store_management_tool_test.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto updatePrice(Long id, ProductPriceDto productPriceDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setPrice(productPriceDto.getPrice());
        productRepository.save(product);

        return productMapper.productToProductDtoMapper(product);
    }
}
