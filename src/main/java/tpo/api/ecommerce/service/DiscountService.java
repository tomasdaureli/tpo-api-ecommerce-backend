package tpo.api.ecommerce.service;

import java.util.List;

import tpo.api.ecommerce.domain.CreateDiscountDTO;
import tpo.api.ecommerce.domain.DiscountDTO;
import tpo.api.ecommerce.domain.UpdateDiscountDTO;

public interface DiscountService {

    List<DiscountDTO> getAllDiscounts();

    DiscountDTO createDiscountCode(CreateDiscountDTO dto);

    DiscountDTO getDiscountById(Long discountId);

    DiscountDTO updateDiscountCode(Long discountId, UpdateDiscountDTO dto);

    void deleteDiscountCode(Long discountId);

}
