package tpo.api.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "item_products")
@IdClass(ItemProductId.class)
public class ItemProduct {

    @Id
    @ManyToOne
    private Buy buy;

    @Id
    @ManyToOne
    private Product product;

    private Integer quantity;

}
