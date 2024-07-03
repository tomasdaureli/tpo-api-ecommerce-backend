package tpo.api.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.CreateDiscountDTO;
import tpo.api.ecommerce.domain.DiscountDTO;
import tpo.api.ecommerce.domain.UpdateDiscountDTO;
import tpo.api.ecommerce.service.DiscountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountService service;

    @GetMapping
    public List<DiscountDTO> getAllDiscounts() {
        return service.getAllDiscounts();
    }

    @PostMapping
    public DiscountDTO createDiscount(
            @Valid @RequestBody CreateDiscountDTO dto) {
        return service.createDiscountCode(dto);
    }

    @GetMapping("/{discountId}")
    public DiscountDTO getDiscountById(
            @PathVariable Long discountId) {
        return service.getDiscountById(discountId);
    }

    @PatchMapping("/{discountId}")
    public DiscountDTO updateDiscount(
            @PathVariable Long discountId,
            @Valid @RequestBody UpdateDiscountDTO dto) {
        return service.updateDiscountCode(discountId, dto);
    }

    @DeleteMapping("/{discountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiscount(
            @PathVariable Long discountId) {
        service.deleteDiscountCode(discountId);
    }

}
