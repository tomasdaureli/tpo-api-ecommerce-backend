package tpo.api.ecommerce.utils;

import tpo.api.ecommerce.repository.UserRepository;
import tpo.api.ecommerce.config.SecurityContextService;
import tpo.api.ecommerce.entity.User;
import tpo.api.ecommerce.entity.UserRoles;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserValidations {

    private final UserRepository userRepository;

    private final SecurityContextService contextService;

    public void validateRole(UserRoles requiredRole) {
        String email = contextService.getAuthenticatedUser();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        if (!requiredRole.equals(user.getRole())) {
            throw new IllegalStateException("No tienes el rol necesario para realizar esta acción");
        }
    }
}
