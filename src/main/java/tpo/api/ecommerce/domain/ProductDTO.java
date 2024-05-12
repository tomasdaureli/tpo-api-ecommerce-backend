package tpo.api.ecommerce.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long id;

    private String productName;

    private BigDecimal price;

    private String urlImage;

    private Integer quantity;

    private String description;

    private CategoryProductDTO category;

    private SubcategoryProductDTO subcategory;

}
