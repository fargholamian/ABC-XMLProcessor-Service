package com.tradedoubler.xmlprocessorservice.service;

import com.tradedoubler.xmlprocessorservice.model.Product;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class XmlParserService {
  Logger logger = LoggerFactory.getLogger(XmlParserService.class);
  public void parseAndStore(String xmlFilePath) throws JAXBException, XMLStreamException {
    JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
    XMLStreamReader reader = xmlFactory.createXMLStreamReader(new StreamSource(xmlFilePath));

    int readerEvent;
    while((readerEvent = reader.next()) != XMLStreamConstants.END_DOCUMENT) {
      while(readerEvent == XMLStreamReader.START_ELEMENT && "product".equals(reader.getLocalName())) {
        Product product = unmarshaller.unmarshal(reader, Product.class).getValue();
        logger.info("Extracted Product:" + product);
        readerEvent = reader.getEventType();
      }
    }
    reader.close();
  }

}
