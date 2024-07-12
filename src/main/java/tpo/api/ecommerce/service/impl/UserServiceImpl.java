package tpo.api.ecommerce.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.config.SecurityContextService;
import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.error.EmailAlreadyRegisteredException;
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

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getAuthenticatedUser() {
        return mapper.toUserDTO(repository.findByEmail(
                contextService.getAuthenticatedUser()).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return mapper.toUserDTO(repository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        if (dto.getEmail() != null
                && userExists(dto.getEmail())) {
            throw new EmailAlreadyRegisteredException(dto.getEmail());
        }

        return repository.findByEmail(contextService.getAuthenticatedUser())
                .map(user -> {
                    user.setName(dto.getName());
                    user.setLastName(dto.getLastName());
                    user.setEmail(dto.getEmail());
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    user.setRole(dto.getRole());
                    return mapper.toUserDTO(repository.save(user));
                })
                .orElseThrow(UserNotFoundException::new);
    }

    private Boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

}
