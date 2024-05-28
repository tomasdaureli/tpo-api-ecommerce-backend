package tpo.api.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import tpo.api.ecommerce.config.JwtService;
import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.AuthenticationResponseDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.entity.User;
import tpo.api.ecommerce.error.InvalidCredentialsException;
import tpo.api.ecommerce.error.UserNotFoundException;
import tpo.api.ecommerce.mapper.AuthMapper;
import tpo.api.ecommerce.repository.UserRepository;
import tpo.api.ecommerce.service.impl.AuthServiceImpl;
import tpo.api.ecommerce.utils.DummyDataUtils;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceImplTests {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthMapper mapper;

    @InjectMocks
    private AuthServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AuthServiceImpl(jwtService, userRepository, passwordEncoder, mapper);
    }

    @Test
    void registerUser() {
        RegisterRequestDTO request = DummyDataUtils.buildRegisterRequestDTO();
        String encryptedPassword = "encryptedPassword";
        User user = DummyDataUtils.buildUser();
        String token = "JwtToken";

        when(passwordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(anyString(), anyString())).thenReturn(token);

        AuthenticationResponseDTO response = service.registerUser(request);

        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(anyString(), anyString());

        assertEquals(token, response.getAccessToken());
    }

    @Test
    void authenticateUser() {
        AuthenticationRequestDTO request = DummyDataUtils.buildAuthenticationRequestDTO();
        User user = DummyDataUtils.buildUser();
        String token = "JwtToken";

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(anyString(), anyString())).thenReturn(token);

        AuthenticationResponseDTO response = service.authenticateUser(request);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(jwtService, times(1)).generateToken(anyString(), anyString());

        assertEquals(token, response.getAccessToken());
    }

    @Test
    void authenticateUserNotFound() {
        AuthenticationRequestDTO request = DummyDataUtils.buildAuthenticationRequestDTO();

        when(userRepository.findByEmail(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> service.authenticateUser(request));
    }

    @Test
    void authenticateUserInvalidCredentials() {
        AuthenticationRequestDTO request = DummyDataUtils.buildAuthenticationRequestDTO();
        User user = DummyDataUtils.buildUser();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> service.authenticateUser(request));
    }

}
