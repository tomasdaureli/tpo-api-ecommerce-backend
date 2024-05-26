package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.AuthenticationResponseDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public AuthenticationResponseDTO registerUser(
            @RequestBody RegisterRequestDTO request) {
        return service.registerUser(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponseDTO authenticateUser(
            @RequestParam AuthenticationRequestDTO request) {
        return service.authenticateUser(request);
    }

}
