package tpo.api.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.CreateBuyRequestDTO;
import tpo.api.ecommerce.domain.CreateBuyResponseDTO;
import tpo.api.ecommerce.entity.Buy;
import tpo.api.ecommerce.entity.BuyStatus;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.error.BuyAlreadyProcessedException;
import tpo.api.ecommerce.error.BuyNotFoundException;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.mapper.BuyMapper;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.repository.BuyRepository;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.impl.BuyServiceImpl;
import tpo.api.ecommerce.utils.DummyDataUtils;

@SpringBootTest
@ActiveProfiles("test")
class BuyServiceImplTests {

    @Mock
    private BuyRepository buyRepository;

    @Mock
    private ProductRepository productRepository;

    @Autowired
    private BuyMapper buyMapper;

    @Mock
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @InjectMocks
    private BuyServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new BuyServiceImpl(buyRepository, productRepository, buyMapper, userService, userMapper);
    }

    @Test
    void createBuy() {
        CreateBuyRequestDTO request = DummyDataUtils.buildCreateBuyRequestDTO();
        Product product = DummyDataUtils.buildProduct();
        Buy buy = DummyDataUtils.buildBuy();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(buyRepository.save(any(Buy.class))).thenReturn(buy);

        CreateBuyResponseDTO response = service.createBuy(request);

        verify(productRepository, times(1)).findById(anyLong());
        verify(buyRepository, times(1)).save(any(Buy.class));

        assertEquals(BigDecimal.valueOf(150), response.getTotal());
    }

    @Test
    void createBuyProductNotFound() {
        CreateBuyRequestDTO request = DummyDataUtils.buildCreateBuyRequestDTO();

        when(productRepository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> service.createBuy(request));
    }

    @Test
    void createBuyProductNoStockWarning() {
        CreateBuyRequestDTO request = DummyDataUtils.buildCreateBuyRequestDTO();
        Product product = DummyDataUtils.buildProduct();
        product.setStock(0);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        CreateBuyResponseDTO response = service.createBuy(request);

        verify(productRepository, times(1)).findById(anyLong());

        assertEquals(product.getProductName(), response.getWarnings().getNoStockProducts().get(0));
    }

    @Test
    void getBuyByNumber() {
        Buy buy = DummyDataUtils.buildBuy();

        when(buyRepository.findById(anyLong())).thenReturn(Optional.of(buy));

        BuyDTO response = service.getBuyByNumber(1l);

        verify(buyRepository, times(1)).findById(anyLong());

        assertEquals("Air Jordan 1 Low", response.getItems().get(0).getProduct().getProductName());
    }

    @Test
    void getBuyByNumberNotFound() {
        when(buyRepository.findById(anyLong())).thenThrow(BuyNotFoundException.class);

        assertThrows(BuyNotFoundException.class, () -> service.getBuyByNumber(100l));
    }

    @Test
    void confirmBuy() {
        Buy buy = DummyDataUtils.buildBuy();

        when(buyRepository.findById(anyLong())).thenReturn(Optional.of(buy));
        when(buyRepository.save(any(Buy.class))).thenReturn(buy);

        BuyDTO response = service.confirmBuy(1l);

        verify(buyRepository, times(1)).findById(anyLong());
        verify(buyRepository, times(1)).save(any(Buy.class));

        assertEquals("Air Jordan 1 Low", response.getItems().get(0).getProduct().getProductName());
        assertEquals(BuyStatus.CONFIRMED.toString(), response.getStatus().toString());
    }

    @Test
    void confirmBuyNotFound() {
        when(buyRepository.findById(anyLong())).thenThrow(BuyNotFoundException.class);

        assertThrows(BuyNotFoundException.class, () -> service.confirmBuy(1l));
    }

    @Test
    void confirmBuyAlreadyProcessed() {
        when(buyRepository.findById(anyLong())).thenThrow(BuyAlreadyProcessedException.class);

        assertThrows(BuyAlreadyProcessedException.class, () -> service.confirmBuy(1l));
    }

    @Test
    void cancelBuy() {
        Buy buy = DummyDataUtils.buildBuy();

        when(buyRepository.findById(anyLong())).thenReturn(Optional.of(buy));
        when(buyRepository.save(any(Buy.class))).thenReturn(buy);

        BuyDTO response = service.cancelBuy(1l);

        verify(buyRepository, times(1)).findById(anyLong());
        verify(buyRepository, times(1)).save(any(Buy.class));

        assertEquals("Air Jordan 1 Low", response.getItems().get(0).getProduct().getProductName());
        assertEquals(BuyStatus.CANCELLED.toString(), response.getStatus().toString());
    }

    @Test
    void cancelBuyNotFound() {
        when(buyRepository.findById(anyLong())).thenThrow(BuyNotFoundException.class);

        assertThrows(BuyNotFoundException.class, () -> service.cancelBuy(1l));
    }

    @Test
    void cancelBuyAlreadyProcessed() {
        when(buyRepository.findById(anyLong())).thenThrow(BuyAlreadyProcessedException.class);

        assertThrows(BuyAlreadyProcessedException.class, () -> service.cancelBuy(1l));
    }

}
