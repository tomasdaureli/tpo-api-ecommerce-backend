package tpo.api.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.CategoryProductDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.SubcategoryProductDTO;
import tpo.api.ecommerce.entity.UserRoles;
import tpo.api.ecommerce.service.ProductService;
import tpo.api.ecommerce.utils.UserValidations;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final UserValidations userValidations;

    @GetMapping
    public List<ProductDTO> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subcategory) {
        return service.getProducts(category, subcategory);
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(
            @PathVariable Long productId) {
        return service.getProductById(productId);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO dto) {
        try {
            userValidations.validateRole(UserRoles.VENDEDOR);
            return ResponseEntity.ok(service.createProduct(dto));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductDTO dto) {
        try {
            userValidations.validateRole(UserRoles.VENDEDOR);
            return ResponseEntity.ok(service.updateProduct(productId, dto));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @GetMapping("/categories")
    public List<CategoryProductDTO> getCategories() {
        return service.getCategories();
    }

    @GetMapping("/categories/subcategories")
    public List<SubcategoryProductDTO> getSubcategories() {
        return service.getSubcategories();
    }

}
