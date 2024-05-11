package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.service.ProductService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductDTO> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(
            @PathVariable Long productId) {
        return service.getProductById(productId);
    }

    @PostMapping
    public ProductDTO createProduct(
            @Valid @RequestBody ProductDTO dto) {
        return service.createProduct(dto);
    }

    @PatchMapping("/{productId}")
    public ProductDTO updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductDTO dto) {
        return service.updateProduct(productId, dto);
    }

}
