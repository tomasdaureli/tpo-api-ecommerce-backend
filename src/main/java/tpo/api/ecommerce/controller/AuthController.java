package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.AuthenticationResponseDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.service.AuthService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public AuthenticationResponseDTO registerUser(
            @Valid @RequestBody RegisterRequestDTO request) {
        return service.registerUser(request);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/authenticate")
    public AuthenticationResponseDTO authenticateUser(
            @Valid @RequestBody AuthenticationRequestDTO request) {
        System.out.println(request);
        return service.authenticateUser(request);
    }

}
