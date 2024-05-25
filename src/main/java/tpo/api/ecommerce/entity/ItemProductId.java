package tpo.api.ecommerce.entity;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class ItemProductId implements Serializable {

    private Long buy;

    private Long product;

}
