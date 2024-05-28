package tpo.api.ecommerce.service;

import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.AuthenticationResponseDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;

public interface AuthService {

    AuthenticationResponseDTO registerUser(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request);

}
