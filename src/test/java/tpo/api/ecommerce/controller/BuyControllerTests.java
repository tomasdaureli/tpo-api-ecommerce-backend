package tpo.api.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import tpo.api.ecommerce.domain.BuyDTO;
import tpo.api.ecommerce.domain.CreateBuyRequestDTO;
import tpo.api.ecommerce.domain.CreateBuyResponseDTO;
import tpo.api.ecommerce.error.BuyAlreadyProcessedException;
import tpo.api.ecommerce.error.BuyNotFoundException;
import tpo.api.ecommerce.service.BuyService;
import tpo.api.ecommerce.utils.DummyDataUtils;

class BuyControllerTests {

    @Mock
    private BuyService service;

    @InjectMocks
    private BuyController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBuy() throws Exception {
        CreateBuyResponseDTO expectedResponse = DummyDataUtils.buildCreateBuyResponseDTO();

        when(service.createBuy(any(CreateBuyRequestDTO.class))).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/buys")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"items\":[{\"productId\":1,\"quantity\":9},{\"productId\":2,\"quantity\":1}]}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).createBuy(any(CreateBuyRequestDTO.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createBuyBadRequest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/buys")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"items\":[{\"productId\":-1,\"quantity\":0},{\"productId\":2,\"quantity\":1}]}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void getBuyByNumber() throws Exception {
        BuyDTO expectedResponse = DummyDataUtils.buildBuyDTO();

        when(service.getBuyByNumber(anyLong())).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/buys/100")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).getBuyByNumber(anyLong());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getBuyByNumberNotFound() throws Exception {
        when(service.getBuyByNumber(anyLong())).thenThrow(BuyNotFoundException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/buys/100")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).getBuyByNumber(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void confirmBuy() throws Exception {
        BuyDTO expectedResponse = DummyDataUtils.buildBuyDTO();

        when(service.confirmBuy(anyLong())).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/buys/100/confirm")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).confirmBuy(anyLong());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void confirmBuyNotFound() throws Exception {
        when(service.confirmBuy(anyLong())).thenThrow(BuyNotFoundException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/buys/100/confirm")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).confirmBuy(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void confirmBuyAlreadyProcessed() throws Exception {
        when(service.confirmBuy(anyLong())).thenThrow(BuyAlreadyProcessedException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/buys/100/confirm")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).confirmBuy(anyLong());

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

    @Test
    void cancelBuy() throws Exception {
        BuyDTO expectedResponse = DummyDataUtils.buildBuyDTO();

        when(service.cancelBuy(anyLong())).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/buys/100/cancel")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).cancelBuy(anyLong());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void cancelBuyNotFound() throws Exception {
        when(service.cancelBuy(anyLong())).thenThrow(BuyNotFoundException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/buys/100/cancel")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).cancelBuy(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void cancelBuyAlreadyProcessed() throws Exception {
        when(service.cancelBuy(anyLong())).thenThrow(BuyAlreadyProcessedException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/buys/100/cancel")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).cancelBuy(anyLong());

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
    }

}
