package tpo.api.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.entity.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product toProduct(ProductDTO dto);

    ProductDTO toProductDTO(Product entity);

    Product toUpdateProduct(ProductDTO dto, @MappingTarget Product entity);

}
