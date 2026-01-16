package com.codewithmosh.store.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto productToProductDto(Product product);

    Product toProductEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    void update (ProductDto productDto, @MappingTarget Product product);
}
