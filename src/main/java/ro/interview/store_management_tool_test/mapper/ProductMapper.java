package ro.interview.store_management_tool_test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.interview.store_management_tool_test.dto.ProductDto;
import ro.interview.store_management_tool_test.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "quantity", source = "stock.quantity")
    ProductDto mapProductToProductDtoMapper(Product product);
}
