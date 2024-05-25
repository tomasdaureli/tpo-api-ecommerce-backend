package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.CreateBuyRequestDTO;
import tpo.api.ecommerce.domain.CreateBuyResponseDTO;
import tpo.api.ecommerce.service.BuyService;

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

    @PostMapping
    public CreateBuyResponseDTO createBuy(
            @Valid @RequestBody CreateBuyRequestDTO request) {
        return service.createBuy(request);
    }

    @GetMapping("/{buyNumber}")
    public BuyDTO getBuyByNumber(
            @PathVariable Long buyNumber) {
        return service.getBuyByNumber(buyNumber);
    }

    @PatchMapping("/{buyNumber}/confirm")
    public BuyDTO confirmBuy(
            @PathVariable Long buyNumber) {
        return service.confirmBuy(buyNumber);
    }

    @PatchMapping("/{buyNumber}/cancel")
    public BuyDTO cancelBuy(
            @PathVariable Long buyNumber) {
        return service.cancelBuy(buyNumber);
    }

}
