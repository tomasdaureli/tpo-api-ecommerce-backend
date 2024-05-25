package tpo.api.ecommerce.service;

import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.CreateBuyRequestDTO;
import tpo.api.ecommerce.domain.CreateBuyResponseDTO;

public interface BuyService {

    CreateBuyResponseDTO createBuy(CreateBuyRequestDTO request);

    BuyDTO getBuyByNumber(Long buyNumber);

    BuyDTO confirmBuy(Long buyNumber);

    BuyDTO cancelBuy(Long buyNumber);

}
