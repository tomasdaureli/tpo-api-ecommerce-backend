package tpo.api.ecommerce.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final AuthMapper mapper;

    // private final AuthenticationManager authManager;

    @Override
    public AuthenticationResponseDTO registerUser(RegisterRequestDTO request) {
        User user = mapper.toUser(request);
        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getId().toString(), user.getEmail());

        return new AuthenticationResponseDTO(accessToken);
    }

    @Override
    public AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request) {
        // authManager.authenticate(
        // new UsernamePasswordAuthenticationToken(request.getEmail(),
        // request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        String accessToken = jwtService.generateToken(user.getId().toString(), user.getEmail());

        return new AuthenticationResponseDTO(accessToken);
    }

}
