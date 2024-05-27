package tpo.api.ecommerce.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.config.JwtService;
import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.AuthenticationResponseDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.entity.User;
import tpo.api.ecommerce.error.UserNotFoundException;
import tpo.api.ecommerce.mapper.AuthMapper;
import tpo.api.ecommerce.repository.UserRepository;
import tpo.api.ecommerce.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthMapper mapper;

    @Override
    public AuthenticationResponseDTO registerUser(RegisterRequestDTO request) {
        User user = mapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getId().toString(), user.getEmail());

        return new AuthenticationResponseDTO(accessToken);
    }

    @Override
    public AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        String accessToken = jwtService.generateToken(user.getId().toString(), user.getEmail());

        return new AuthenticationResponseDTO(accessToken);
    }

}
