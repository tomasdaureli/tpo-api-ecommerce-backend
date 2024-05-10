package tpo.api.ecommerce.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @Entity
public class Product {

    private Long id;

    private String productName;

    private BigDecimal price;

    private String urlImage;

    private Integer quantity;

    private String description;

}
