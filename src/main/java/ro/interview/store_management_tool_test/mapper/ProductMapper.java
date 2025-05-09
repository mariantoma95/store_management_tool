package ro.interview.store_management_tool_test.mapper;

import org.mapstruct.Mapper;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto productToProductDtoMapper(Product product);
}
