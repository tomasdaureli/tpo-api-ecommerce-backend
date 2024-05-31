package tpo.api.ecommerce.service;

import tpo.api.ecommerce.domain.UserDTO;

public interface UserService {

    UserDTO getAuthenticatedUser();

    UserDTO updateUser(UserDTO dto);

    UserDTO getUserById(Long id);

}
