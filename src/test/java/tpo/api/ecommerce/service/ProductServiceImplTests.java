package tpo.api.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.mapper.ProductMapper;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.impl.ProductServiceImpl;
import tpo.api.ecommerce.utils.DummyDataUtils;

@SpringBootTest
class ProductServiceImplTests {

    @Mock
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ProductServiceImpl(repository, mapper);
    }

    @Test
    void testGetProducts() {
        List<Product> products = List.of(DummyDataUtils.buildProduct());

        when(repository.findAll()).thenReturn(products);

        List<ProductDTO> response = service.getProducts();

        verify(repository, times(1)).findAll();

        assertEquals("Air Jordan 1 Low", response.get(0).getProductName());
    }

    @Test
    void testGetProductById() {
        Product product = DummyDataUtils.buildProduct();

        when(repository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDTO response = service.getProductById(1L);

        verify(repository, times(1)).findById(anyLong());

        assertEquals("Air Jordan 1 Low", response.getProductName());
    }

    @Test
    void testGetProductByIdThrowsProductNotFoundException() {

        when(repository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> service.getProductById(100L));

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testCreateProduct() {
        ProductDTO request = DummyDataUtils.buildProductDTO();
        Product product = DummyDataUtils.buildProduct();

        when(repository.save(any(Product.class))).thenReturn(product);

        ProductDTO response = service.createProduct(request);

        verify(repository, times(1)).save(any(Product.class));

        assertEquals("Air Jordan 1 Low", response.getProductName());
    }

    @Test
    void testUpdateProduct() {
        ProductDTO request = DummyDataUtils.buildProductDTO();
        Product product = DummyDataUtils.buildProduct();

        when(repository.findById(anyLong())).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        ProductDTO response = service.updateProduct(1L, request);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Product.class));

        assertEquals("Air Jordan 1 Low", response.getProductName());
    }

    @Test
    void testUpdateProductThrowsProductNotFoundException() {
        ProductDTO request = DummyDataUtils.buildProductDTO();

        when(repository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> service.updateProduct(100L, request));

        verify(repository, times(1)).findById(anyLong());
    }

}
