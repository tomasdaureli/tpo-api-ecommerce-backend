package tpo.api.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.service.ProductService;
import tpo.api.ecommerce.utils.DummyDataUtils;

class ProductControllerTests {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    private static final String PRODUCT_BODY = "{\"productName\": \"Nike Air Force 1 07 LX\", \"price\": 69.99, \"urlImage\": \"https://nikearprod.vtexassets.com/arquivos/ids/773739-1200-1200?width=1200&height=1200&aspect=true\", \"quantity\": 1, \"description\": \"Materiales premium. Acabados envejecidos. Comodidad amortiguada. Este modelo AF1 ofrece un estilo discreto y agrega el toque perfecto a tu look. Con su estilo de básquetbol retro y cuello cómodo de corte low, mantuvimos todo lo que amas de este básico de estilo moderno.\"}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts() throws Exception {
        List<ProductDTO> expectedResponse = List.of(DummyDataUtils.buildProductDTO());

        when(service.getProducts()).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).getProducts();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDTO expectedResponse = DummyDataUtils.buildProductDTO();

        when(service.getProductById(anyLong())).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/1")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).getProductById(anyLong());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetProductByIdThrowsProductNotFoundException() throws Exception {

        when(service.getProductById(anyLong())).thenThrow(ProductNotFoundException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/100")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).getProductById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO expectedResponse = DummyDataUtils.buildProductDTO();

        when(service.createProduct(any(ProductDTO.class))).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PRODUCT_BODY);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).createProduct(any(ProductDTO.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO expectedResponse = DummyDataUtils.buildProductDTO();

        when(service.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PRODUCT_BODY);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).updateProduct(anyLong(), any(ProductDTO.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testUpdateProductThrowsProductNotFoundException() throws Exception {

        when(service.updateProduct(anyLong(), any(ProductDTO.class))).thenThrow(ProductNotFoundException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/products/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"productName\": \"Nike Air Force 1 07 LX\", \"price\": 69.99, \"urlImage\": \"https://nikearprod.vtexassets.com/arquivos/ids/773739-1200-1200?width=1200&height=1200&aspect=true\", \"quantity\": 1, \"description\": \"Materiales premium. Acabados envejecidos. Comodidad amortiguada. Este modelo AF1 ofrece un estilo discreto y agrega el toque perfecto a tu look. Con su estilo de básquetbol retro y cuello cómodo de corte low, mantuvimos todo lo que amas de este básico de estilo moderno.\"}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).updateProduct(anyLong(), any(ProductDTO.class));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

}
