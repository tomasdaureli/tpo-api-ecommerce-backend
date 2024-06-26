package tpo.api.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.test.context.ActiveProfiles;

import tpo.api.ecommerce.domain.CategoryProductDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.SubcategoryProductDTO;
import tpo.api.ecommerce.entity.CategoryProduct;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.entity.SubcategoryProduct;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.mapper.ProductMapper;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.impl.ProductServiceImpl;
import tpo.api.ecommerce.utils.DummyDataUtils;

@SpringBootTest
@ActiveProfiles("test")
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
        String category = null;
        String subcategory = null;

        when(repository.findByStockGreaterThan(0)).thenReturn(products);

        List<ProductDTO> response = service.getProducts(category, subcategory);

        verify(repository, times(1)).findByStockGreaterThan(0);

        assertEquals("Air Jordan 1 Low", response.get(0).getProductName());
    }

    @Test
    void testGetProductsByCategories() {
        List<Product> products = List.of(DummyDataUtils.buildProduct());
        String category = "FOOTWEAR";
        String subcategory = null;

        when(repository.findByCategoryAndStockGreaterThan(any(CategoryProduct.class), anyInt()))
                .thenReturn(products);

        List<ProductDTO> response = service.getProducts(category, subcategory);

        verify(repository, times(1)).findByCategoryAndStockGreaterThan(any(CategoryProduct.class), anyInt());

        assertEquals("Air Jordan 1 Low", response.get(0).getProductName());
    }

    @Test
    void testGetProductsBySubcategories() {
        List<Product> products = List.of(DummyDataUtils.buildProduct());
        String category = null;
        String subcategory = "FASHION";

        when(repository.findBySubcategoryAndStockGreaterThan(any(SubcategoryProduct.class), anyInt()))
                .thenReturn(products);

        List<ProductDTO> response = service.getProducts(category, subcategory);

        verify(repository, times(1)).findBySubcategoryAndStockGreaterThan(any(SubcategoryProduct.class), anyInt());

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

    @Test
    void testGetCategories() {
        List<CategoryProductDTO> expectedResponse = DummyDataUtils.buildCategoriesList();

        List<CategoryProductDTO> response = service.getCategories();

        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetSubcategories() {
        List<SubcategoryProductDTO> expectedResponse = DummyDataUtils.buildSubcategoriesList();

        List<SubcategoryProductDTO> response = service.getSubcategories();

        assertEquals(expectedResponse, response);
    }

}
