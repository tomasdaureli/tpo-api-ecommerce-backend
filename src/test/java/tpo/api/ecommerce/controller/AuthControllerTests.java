package tpo.api.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import tpo.api.ecommerce.domain.AuthenticationRequestDTO;
import tpo.api.ecommerce.domain.AuthenticationResponseDTO;
import tpo.api.ecommerce.domain.RegisterRequestDTO;
import tpo.api.ecommerce.service.AuthService;

class AuthControllerTests {

    @Mock
    private AuthService service;

    @InjectMocks
    private AuthController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserOk() throws Exception {
        AuthenticationResponseDTO expectedResponse = new AuthenticationResponseDTO();

        when(service.registerUser(any(RegisterRequestDTO.class))).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"Tomas\", \"lastName\": \"Pepito\", \"email\": \"tomaspepito@gmail.com\", \"password\": \"12345\"}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).registerUser(any(RegisterRequestDTO.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void registerUserBadRequest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"Tomas\", \"lastName\": \"Pepito\", \"email\": \"tomaspepitogmailcom\", \"password\": \"12345\"}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void authenticateUserOk() throws Exception {
        AuthenticationResponseDTO expectedResponse = new AuthenticationResponseDTO();

        when(service.authenticateUser(any(AuthenticationRequestDTO.class))).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"email\": \"tomaspepito@gmail.com\", \"password\": \"12345\"}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).authenticateUser(any(AuthenticationRequestDTO.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void authenticateUserBadRequest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"email\": \"tomaspepitogmailcom\", \"password\": \"12345\"}");

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}
