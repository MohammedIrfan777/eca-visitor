package com.eca.visitor.controller;

import com.eca.visitor.dto.DataItem;
import com.eca.visitor.dto.OwnerDTO;
import com.eca.visitor.dto.UserRespDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.eca.visitor.utils.JsonUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:application-test.yaml")
@ActiveProfiles("test")
public class VisitorRegistrationControllerTest {

    public static final String REG_URL_PATH = "/v1/visitor/registration";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${microservice.user-service.endpoints.endpoint.uri}")
    private String endpointUrl;

    @Autowired
    private ModelMapper modelMapper;

    @SneakyThrows
    @Test
    void checkVisitorRegistrationForOwner() {
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        ResponseEntity<UserRespDTO> responseEntityMock = Mockito.mock(ResponseEntity.class);
        String ownerJson = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/ownerUserResponse.json")),
                StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        var ownerResponse = objectMapper.readValue(ownerJson, UserRespDTO.class);
        Mockito.when(responseEntityMock.getBody()).thenReturn(ownerResponse);
        mockRestTemplate(restTemplateMock,responseEntityMock);
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.visitorId").exists())
                .andExpect(jsonPath("$.data.apartmentId").exists())
              .andExpect(jsonPath("$.data.visitorFirstName",is(equalTo("TestVisitor"))));
    }

    private void mockRestTemplate(RestTemplate restTemplateMock, ResponseEntity<UserRespDTO> responseEntityMock) {
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),  // URL
                Mockito.eq(HttpMethod.GET),  // HTTP method
                Mockito.any(),  // Request entity
                Mockito.eq(UserRespDTO.class)  // Response type
        )).thenReturn(responseEntityMock);
        restTemplate = restTemplateMock;
    }

    @SneakyThrows
    @Test
    void checkVisitorRegistrationForTenant() {
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        ResponseEntity<UserRespDTO> responseEntityMock = Mockito.mock(ResponseEntity.class);
        String tenantJson = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/tenantUserResponse.json")),
                StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        var tenantResponse = objectMapper.readValue(tenantJson, UserRespDTO.class);
        Mockito.when(responseEntityMock.getBody()).thenReturn(tenantResponse);
        mockRestTemplate(restTemplateMock,responseEntityMock);
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.visitorId").exists())
                .andExpect(jsonPath("$.data.apartmentId").exists())
                .andExpect(jsonPath("$.data.visitorFirstName",is(equalTo("TestVisitor"))));
    }

    @SneakyThrows
    @Test
    void checkVisitorRegistrationForVendor() {
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        ResponseEntity<UserRespDTO> responseEntityMock = Mockito.mock(ResponseEntity.class);
        String vendorJson = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/vendorUserResponse.json")),
                StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        var vendorResponse = objectMapper.readValue(vendorJson, UserRespDTO.class);
        Mockito.when(responseEntityMock.getBody()).thenReturn(vendorResponse);
        mockRestTemplate(restTemplateMock,responseEntityMock);
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.visitorId").exists())
                .andExpect(jsonPath("$.data.apartmentId").exists())
                .andExpect(jsonPath("$.data.visitorFirstName",is(equalTo("TestVisitor"))));
    }

   @Test
    void checkVisitorInvalidRegistration() throws Exception {
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/invalidVisitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", is(notNullValue())));
    }


}
