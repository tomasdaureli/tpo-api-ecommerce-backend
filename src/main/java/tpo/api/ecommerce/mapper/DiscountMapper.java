package tpo.api.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import tpo.api.ecommerce.domain.CreateDiscountDTO;
import tpo.api.ecommerce.domain.DiscountDTO;
import tpo.api.ecommerce.domain.UpdateDiscountDTO;
import tpo.api.ecommerce.entity.Discount;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiscountMapper {

    DiscountDTO toDiscountDTO(Discount discount);

    Discount toDiscountFromCreateDTO(CreateDiscountDTO dto);

    Discount toDiscountFromUpdateDTO(UpdateDiscountDTO dto, @MappingTarget Discount discount);

}
