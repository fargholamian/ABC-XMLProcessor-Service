package com.tradedoubler.xmlprocessorservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradedoubler.xmlprocessorservice.configuration.AuthenticationInterceptor;
import com.tradedoubler.xmlprocessorservice.enums.Role;
import com.tradedoubler.xmlprocessorservice.model.Product;
import com.tradedoubler.xmlprocessorservice.model.User;

import com.tradedoubler.xmlprocessorservice.repo.ProductRepository;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  AuthenticationInterceptor interceptor;

  @MockBean
  private ProductRepository productRepository;

  private final User user = new User(UUID.randomUUID(), "user", "password" , Role.ROLE_USER);

  @Test
  public void shouldUploadAPIReturn200WithProperJsonResult_WhenParamsAreOkAndRepositoryReturnProductList() throws Exception {
    List<Product> expectedProducts = generateProducts();
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(productRepository.findByEvent_UserId(any(), any())).thenReturn(generateProducts());

    ResultActions resultActions = mockMvc.perform(
        get("/api/product")
            .param("userId", UUID.randomUUID().toString())
            .param("format", "json")
            .contentType(APPLICATION_JSON_VALUE));

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$", hasSize(3)));
    resultActions.andExpect(jsonPath("$.[0].groupingId").value("skuSKU1-TV0000011"));
    resultActions.andExpect(jsonPath("$.[1].groupingId").value("skuSKU1-TV0000012"));
    resultActions.andExpect(jsonPath("$.[2].groupingId").value("skuSKU1-TV000001"));
  }

  @Test
  public void shouldUploadAPIReturn200WithProperCsvResult_WhenParamsAreOkAndRepositoryReturnProductList() throws Exception {
    List<Product> expectedProducts = generateProducts();
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(productRepository.findByEvent_UserId(any(), any())).thenReturn(generateProducts());

    ResultActions resultActions = mockMvc.perform(
        get("/api/product")
            .param("userId", UUID.randomUUID().toString())
            .param("format", "csv")
            .contentType(APPLICATION_JSON_VALUE));

    resultActions.andExpect(status().isOk());
    List<String> lines = resultActions.andReturn().getResponse().getContentAsString().lines().toList();
    assertThat(lines.size()).isEqualTo(4);
    assertThat(lines.get(0)).contains("groupingId");
    assertThat(lines.get(1)).contains("skuSKU1-TV0000011");
    assertThat(lines.get(2)).contains("skuSKU1-TV0000012");
    assertThat(lines.get(3)).contains("skuSKU1-TV000001");
  }

  @Test
  public void shouldUploadAPIReturn200WithProperXmlResult_WhenParamsAreOkAndRepositoryReturnProductList() throws Exception {
    List<Product> expectedProducts = generateProducts();
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(productRepository.findByEvent_UserId(any(), any())).thenReturn(generateProducts());

    ResultActions resultActions = mockMvc.perform(
        get("/api/product")
            .param("userId", UUID.randomUUID().toString())
            .param("format", "xml")
            .contentType(APPLICATION_JSON_VALUE));

    resultActions.andExpect(status().isOk());
    String body = resultActions.andReturn().getResponse().getContentAsString();
    assertThat(body).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
    assertThat(StringUtils.countOccurrencesOf(body, "</products>")).isEqualTo(1);
    assertThat(StringUtils.countOccurrencesOf(body, "</product>")).isEqualTo(3);
    assertThat(body).contains("<product groupingId=\"skuSKU1-TV0000011\" language=\"sv\">");
    assertThat(body).contains("<product groupingId=\"skuSKU1-TV0000012\" language=\"sv\">");
    assertThat(body).contains("<product groupingId=\"skuSKU1-TV000001\" language=\"sv\">");
  }


  @Test
  public void shouldUploadAPIReturn200WithEmptyList_WhenParamsAreOkAndRepositoryReturnEmptyList() throws Exception {
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(productRepository.findByEvent_UserId(any(), any())).thenReturn(Collections.emptyList());

    ResultActions resultActions = mockMvc.perform(
        get("/api/product")
            .param("userId", UUID.randomUUID().toString())
            .param("format", "json")
            .contentType(APPLICATION_JSON_VALUE));

    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void shouldUploadAPIReturn500_WhenParamsAreOkButRepositoryThrewException() throws Exception {
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(productRepository.findByEvent_UserId(any(), any())).thenThrow(new RuntimeException("Something bad happened"));

    ResultActions resultActions = mockMvc.perform(
        get("/api/product")
            .param("userId", UUID.randomUUID().toString())
            .param("format", "json")
            .contentType(APPLICATION_JSON_VALUE));

    resultActions.andExpect(status().isInternalServerError());
  }

  private List<Product> generateProducts() throws IOException {
    Resource productResources = new ClassPathResource("files/Products.json");
    TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>(){};
    ObjectMapper mapper = new ObjectMapper();

    return mapper.readValue(productResources.getFile(),  typeReference);
  }
}
