package tpo.api.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.*;
import tpo.api.ecommerce.entity.*;
import tpo.api.ecommerce.error.*;
import tpo.api.ecommerce.mapper.BuyMapper;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.repository.BuyRepository;
import tpo.api.ecommerce.repository.DiscountRepository;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.BuyService;
import tpo.api.ecommerce.service.UserService;

@Service
@RequiredArgsConstructor
public class BuyServiceImpl implements BuyService {

    private final BuyRepository buyRepository;

    private final ProductRepository productRepository;

    private final BuyMapper buyMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    private final DiscountRepository discountRepository;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public CreateBuyResponseDTO createBuy(CreateBuyRequestDTO request) {
        CreateBuyResponseDTO response = new CreateBuyResponseDTO();
        Buy buy = new Buy();

        List<ItemProduct> items = addItemsToBuy(request.getItems(), response, buy);

        if (items.isEmpty()) {
            return response;
        }

        buy.setItems(items);
        buy.setTotal(calculateTotal(items, Optional.ofNullable(request.getDiscountCode())));
        buy.setStatus(BuyStatus.PENDING);
        buy.setBuyer(userMapper.toUser(userService.getAuthenticatedUser()));

        return buyMapper.toCreateBuyResponseDTO(buyRepository.save(buy), response);
    }

    private List<ItemProduct> addItemsToBuy(List<CreateItemProductDTO> request, CreateBuyResponseDTO response,
            Buy buy) {
        List<ItemProduct> items = new ArrayList<>();
        request.forEach(i -> {
            Product product = productRepository.findById(i.getProductId())
                    .orElseThrow(ProductNotFoundException::new);
            if (Boolean.TRUE.equals(verifyStock(product, i.getQuantity()))) {
                ItemProduct item = new ItemProduct();
                item.setBuy(buy);
                item.setProduct(product);
                item.setQuantity(i.getQuantity());
                items.add(item);
            } else {
                if (response.getWarnings() == null) {
                    WarningDTO warnings = new WarningDTO();
                    warnings.setNoStockProducts(new ArrayList<>());
                    response.setWarnings(warnings);
                }
                response.getWarnings().getNoStockProducts().add(product.getProductName());
            }
        });
        return items;
    }

    private Boolean verifyStock(Product product, Integer quantity) {
        return product.getStock() >= quantity;
    }

    private BigDecimal calculateTotal(List<ItemProduct> items, Optional<String> discountCode) {
        BigDecimal total = items.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (discountCode.isPresent()) {
            Discount discount = getDiscountByCode(discountCode.get());
            if (discount != null) {
                BigDecimal discountAmount = total
                        .multiply(BigDecimal.valueOf(discount.getAmount()).divide(BigDecimal.valueOf(100)));
                total = total.subtract(discountAmount);
            }
        }

        return total;
    }

    private Discount getDiscountByCode(String discountCode) {
        return discountRepository.findByCode(discountCode)
                .orElseThrow(DiscountNotFoundException::new);
    }

    @Override
    public BuyDTO getBuyByNumber(Long buyNumber) {
        return buyMapper.toBuyDTO(buyRepository.findById(buyNumber)
                .orElseThrow(BuyNotFoundException::new));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public BuyDTO confirmBuy(Long buyNumber) {
        Buy buy = buyRepository.findById(buyNumber)
                .orElseThrow(BuyNotFoundException::new);

        if (BuyStatus.CANCELLED.equals(buy.getStatus())
                || BuyStatus.CONFIRMED.equals(buy.getStatus())) {
            throw new BuyAlreadyProcessedException(buy.getStatus().toString());
        }

        buy.getItems().forEach(i -> {
            if (!Boolean.TRUE.equals(verifyStock(i.getProduct(), i.getQuantity()))) {
                throw new ProductWithoutStockException(i.getProduct().getProductName());
            }
        });
        buy.setStatus(BuyStatus.CONFIRMED);
        updateProductsStock(buy.getItems());

        return buyMapper.toBuyDTO(buyRepository.save(buy));
    }

    private void updateProductsStock(List<ItemProduct> products) {
        products.forEach(p -> {
            p.getProduct().setStock(p.getProduct().getStock() - p.getQuantity());
            productRepository.save(p.getProduct());
        });
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public BuyDTO cancelBuy(Long buyNumber) {
        Buy buy = buyRepository.findById(buyNumber)
                .orElseThrow(BuyNotFoundException::new);

        if (BuyStatus.CANCELLED.equals(buy.getStatus())
                || BuyStatus.CONFIRMED.equals(buy.getStatus())) {
            throw new BuyAlreadyProcessedException(buy.getStatus().toString());
        }

        buy.setStatus(BuyStatus.CANCELLED);

        return buyMapper.toBuyDTO(buyRepository.save(buy));
    }
}
