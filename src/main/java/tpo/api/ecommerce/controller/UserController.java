package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public UserDTO getAuthenticatedUser() {
        return service.getAuthenticatedUser();
    }

}
