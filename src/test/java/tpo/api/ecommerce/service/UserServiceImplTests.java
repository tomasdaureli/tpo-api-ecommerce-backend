package tpo.api.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import tpo.api.ecommerce.config.SecurityContextService;
import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.entity.User;
import tpo.api.ecommerce.error.UserNotFoundException;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.repository.UserRepository;
import tpo.api.ecommerce.service.impl.UserServiceImpl;
import tpo.api.ecommerce.utils.DummyDataUtils;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTests {

    @Mock
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Mock
    private SecurityContextService contextService;

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserServiceImpl(repository, mapper, contextService, passwordEncoder);
    }

    @Test
    void getAuthenticatedUser() {
        String expectedEmail = "test@test.com";
        User user = DummyDataUtils.buildUser();

        when(contextService.getAuthenticatedUser()).thenReturn(expectedEmail);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDTO response = service.getAuthenticatedUser();

        verify(contextService, times(1)).getAuthenticatedUser();
        verify(repository, times(1)).findByEmail(anyString());

        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void getAuthenticatedUserNotFoundException() {
        when(repository.findByEmail(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> service.getAuthenticatedUser());
    }
}
