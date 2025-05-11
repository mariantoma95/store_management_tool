package ro.interview.store_management_tool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.interview.store_management_tool.dto.ProductDto;
import ro.interview.store_management_tool.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "quantity", source = "stock.quantity")
    ProductDto mapProductToProductDtoMapper(Product product);
}
