package ro.interview.store_management_tool_test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.dto.ProductPriceDto;
import ro.interview.store_management_tool_test.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto updatePrice(Long id, ProductPriceDto productPriceDto) {


        return new ProductDto();
    }
}
