package tpo.api.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "buys", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(RegisterRequestDTO request);

}
