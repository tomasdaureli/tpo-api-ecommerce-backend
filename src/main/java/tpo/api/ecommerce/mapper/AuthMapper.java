package tpo.api.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {

    User toUser(RegisterRequestDTO request);

    

}
