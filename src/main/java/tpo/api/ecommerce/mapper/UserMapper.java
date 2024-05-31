package tpo.api.ecommerce.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.ItemProductDTO;
import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.domain.UserResponseDTO;
import tpo.api.ecommerce.entity.Buy;
import tpo.api.ecommerce.entity.ItemProduct;
import tpo.api.ecommerce.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDTO toUserDTO(User entity);

    UserResponseDTO toUserResponseDTO(UserDTO dto);

    User toUser(UserDTO dto);

    Buy toBuy(BuyDTO dto);

    ItemProduct toItemProduct(ItemProductDTO dto);

    default List<Buy> toBuys(List<BuyDTO> dtos) {
        return dtos.stream()
                .map(this::toBuy)
                .collect(Collectors.toList());
    }

    default List<ItemProduct> toItems(List<ItemProductDTO> dtos) {
        return dtos.stream()
                .map(this::toItemProduct)
                .collect(Collectors.toList());
    }

}
