package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.domain.UserResponseDTO;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/me")
    public UserResponseDTO getAuthenticatedUser() {
        return mapper.toUserResponseDTO(service.getAuthenticatedUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toUserResponseDTO(service.getUserById(id)));
    }

    @PutMapping("/change")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserDTO userDto) {
        UserDTO updatedUser = service.updateUser(userDto);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toUserResponseDTO(updatedUser));
    }

}
