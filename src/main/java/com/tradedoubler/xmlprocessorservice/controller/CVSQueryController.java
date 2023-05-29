package com.tradedoubler.xmlprocessorservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradedoubler.xmlprocessorservice.enums.OutputFormats;
import com.tradedoubler.xmlprocessorservice.model.Product;
import com.tradedoubler.xmlprocessorservice.model.Products;
import com.tradedoubler.xmlprocessorservice.repo.ProductRepository;
import com.tradedoubler.xmlprocessorservice.utils.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Max;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/product")
@Validated
public class CVSQueryController {
  private final static Logger logger = LoggerFactory.getLogger(CVSQueryController.class);

  private final ProductRepository productRepository;

  @GetMapping
  public void getProductByUserId(@RequestParam UUID userId,
                                 @RequestParam(required = false) OutputFormats format,
                                 @RequestParam(defaultValue = "0", required = false) Integer offset,
                                 @RequestParam(defaultValue = "10", required = false) @Max(value = 10, message = "Size should be less than 10") Integer size,
                                 HttpServletResponse response) throws IOException, JAXBException {

    Pageable paging = PageRequest.of(offset, size, Sort.by("id"));
    List<Product> productList = productRepository.findByEvent_UserId(userId, paging);
    if (OutputFormats.CSV.equals(format)) {
      sendResultInCsvFormat(productList, response);
      return;
    }

    if (OutputFormats.XML.equals(format)) {
      sendResultInXmlFormat(productList, response);
      return;
    }

    sendResultInJsonFormat(productList, response);
  }

  public void sendResultInCsvFormat(List<Product> productList, HttpServletResponse response) throws IOException {
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",
        UUID.randomUUID()));
    CSVWriter.writeToCsv(productList, response.getWriter());
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public void sendResultInJsonFormat(List<Product> productList, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Writer write = response.getWriter();
    productList.forEach(product -> {
      try {
        write.write(new ObjectMapper().writeValueAsString(product));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public void sendResultInXmlFormat(List<Product> productList, HttpServletResponse response)
      throws IOException, JAXBException {
    response.setContentType("text/xml");
    Writer write = response.getWriter();
    JAXBContext context = JAXBContext.newInstance(Products.class);
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.marshal(new Products(productList), write);
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
