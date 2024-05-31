package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.CreateBuyRequestDTO;
import tpo.api.ecommerce.entity.UserRoles;
import tpo.api.ecommerce.service.BuyService;
import tpo.api.ecommerce.utils.UserValidations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buys")
public class BuyController {

    private final BuyService service;
    private final UserValidations userValidations;

    @PostMapping
    public ResponseEntity<?> createBuy(
            @Valid @RequestBody CreateBuyRequestDTO request) {
        try {
            userValidations.validateRole(UserRoles.COMPRADOR);
            return ResponseEntity.ok(service.createBuy(request));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/{buyNumber}")
    public ResponseEntity<?> getBuyByNumber(
            @PathVariable Long buyNumber) {
        try {
            userValidations.validateRole(UserRoles.COMPRADOR);
            return ResponseEntity.ok(service.getBuyByNumber(buyNumber));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @PatchMapping("/{buyNumber}/confirm")
    public ResponseEntity<?> confirmBuy(
            @PathVariable Long buyNumber) {
        try {
            userValidations.validateRole(UserRoles.COMPRADOR);
            return ResponseEntity.ok(service.confirmBuy(buyNumber));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @PatchMapping("/{buyNumber}/cancel")
    public ResponseEntity<?> cancelBuy(
            @PathVariable Long buyNumber) {
        try {
            userValidations.validateRole(UserRoles.COMPRADOR);
            return ResponseEntity.ok(service.cancelBuy(buyNumber));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

}
