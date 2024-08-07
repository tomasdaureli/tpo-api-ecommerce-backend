package tpo.api.ecommerce.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {

    private Long id;

    private String productName;

    private BigDecimal price;

    private String urlImage;

    private Integer stock;

    private String description;

    private CategoryProductDTO category;

    private SubcategoryProductDTO subcategory;

    private UserDTO seller;

    private Integer sold;

    private Boolean active;

}
