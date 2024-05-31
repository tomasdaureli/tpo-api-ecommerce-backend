package tpo.api.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import tpo.api.ecommerce.domain.UserDTO;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.service.UserService;

class UserControllerTests {

    @Mock
    private UserService service;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuthenticatedUser() throws Exception {
        UserDTO expectedResponse = new UserDTO();

        when(service.getAuthenticatedUser()).thenReturn(expectedResponse);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/me")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        verify(service, times(1)).getAuthenticatedUser();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
