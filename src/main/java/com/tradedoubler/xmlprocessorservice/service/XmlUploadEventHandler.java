package com.tradedoubler.xmlprocessorservice.service;

import com.tradedoubler.xmlprocessorservice.model.Event;
import com.tradedoubler.xmlprocessorservice.model.Product;
import com.tradedoubler.xmlprocessorservice.repo.ProductRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XmlUploadEventHandler implements EventHandler {
  Logger logger = LoggerFactory.getLogger(XmlUploadEventHandler.class);

  private final ProductRepository productRepository;

  @Value("${application.directories.uploaded}")
  private String uploadedFileDirectory;

  @Override
  public void handle(Event event) {

    parseAndStore(event);
  }

  private void parseAndStore(Event event) {
    try {
      Path path = Paths.get(uploadedFileDirectory + File.separator + event.getFilename());
      if (Files.notExists(path)) {
        throw new RuntimeException("Can't find the uploaded file: " + event.getFilename() + " for eventId: " + event.getId());
      }

      JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
      XMLStreamReader reader = xmlFactory.createXMLStreamReader(new StreamSource(path.toString()));

      int readerEvent;
      while ((readerEvent = reader.next()) != XMLStreamConstants.END_DOCUMENT) {
        while (readerEvent == XMLStreamReader.START_ELEMENT && "product".equals(reader.getLocalName())) {
          Product product = unmarshaller.unmarshal(reader, Product.class).getValue();
          product.setEvent(event);
          logger.info("Extracted Product:" + product);
          logger.info("Extracted Event:" + product.getEvent());
          productRepository.save(product);
          readerEvent = reader.getEventType();
        }
      }
      reader.close();

    } catch (JAXBException | XMLStreamException e) {
      throw new RuntimeException(
          "XML parse error file: " + event.getFilename() + " for userId: " + event.getUserId() + " and eventId: " +
              event.getId());
    }
  }
}
