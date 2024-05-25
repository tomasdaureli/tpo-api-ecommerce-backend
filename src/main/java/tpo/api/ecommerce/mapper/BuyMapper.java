package tpo.api.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.CreateBuyResponseDTO;
import tpo.api.ecommerce.entity.Buy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BuyMapper {

    BuyDTO toBuyDTO(Buy entity);

    @Mapping(target = "warnings", ignore = true)
    CreateBuyResponseDTO toCreateBuyResponseDTO(Buy entity, @MappingTarget CreateBuyResponseDTO response);

}
