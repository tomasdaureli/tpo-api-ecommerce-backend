package tpo.api.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.config.SecurityContextService;
import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.error.UserNotFoundException;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.repository.UserRepository;
import tpo.api.ecommerce.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final SecurityContextService contextService;

    @Override
    public UserDTO getAuthenticatedUser() {
        return mapper.toUserDTO(repository.findByEmail(
                contextService.getAuthenticatedUser()).orElseThrow(UserNotFoundException::new));
    }

}
