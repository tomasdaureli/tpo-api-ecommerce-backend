package tpo.api.ecommerce.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    @Mapping(target = "discount", source = "entity.discount")
    @Mapping(target = "total", expression = "java(mapTotal(entity.getTotal()))")
    CreateBuyResponseDTO toCreateBuyResponseDTO(Buy entity, @MappingTarget CreateBuyResponseDTO response);

    default BigDecimal mapTotal(BigDecimal total) {
        return total.setScale(2, RoundingMode.HALF_UP);
    }

}
