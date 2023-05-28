package com.tradedoubler.xmlprocessorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradedoubler.xmlprocessorservice.model.Event;
import com.tradedoubler.xmlprocessorservice.repo.EventRepository;
import jakarta.xml.bind.JAXBException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.stream.XMLStreamException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventConsumer {
  Logger logger = LoggerFactory.getLogger(EventConsumer.class);

  @Value("${application.directories.uploaded}")
  private String uploadedFileDirectory;

  private final EventRepository eventRepository;

  private final XmlParserService xmlParserService;

  @KafkaListener(topics = "file-received-event")
  public void receive(String message) throws JsonProcessingException {
    logger.info("Event received: " + message);
    Event event = new ObjectMapper().readValue(message, Event.class);

    Path path = Paths.get(uploadedFileDirectory + "/" + event.getFilename());
    if (Files.notExists(path)) {
      throw new RuntimeException("Can't find the uploaded file: " + event.getFilename() + " for eventId: " + event.getId());
    }

    try {
      eventRepository.save(event);
    } catch (DataIntegrityViolationException e) {
      throw new RuntimeException(
          "Duplicate file: " + event.getFilename() + " for userId: " + event.getUserId() + " and eventId: " + event.getId());
    }

    try {
      xmlParserService.parseAndStore(path.toString());
    } catch (JAXBException | XMLStreamException e) {
      throw new RuntimeException(
          "XML parse error file: " + event.getFilename() + " for userId: " + event.getUserId() + " and eventId: " + event.getId());
    }

    logger.info("Event successfully processed: " + event);
  }
}
