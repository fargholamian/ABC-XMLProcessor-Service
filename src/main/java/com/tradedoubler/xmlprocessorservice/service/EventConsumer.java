package com.tradedoubler.xmlprocessorservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradedoubler.xmlprocessorservice.model.Event;
import com.tradedoubler.xmlprocessorservice.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventConsumer {
  Logger logger = LoggerFactory.getLogger(EventConsumer.class);

  private final XmlUploadEventHandler xmlUploadEventHandler;

  private final EventRepository eventRepository;

  @KafkaListener(topics = "file-received-event")
  public void receive(String message) throws JsonProcessingException {
    logger.info("Event received: " + message);
    Event event = new ObjectMapper().readValue(message, Event.class);
    eventRepository.save(event);
    xmlUploadEventHandler.handle(event);
    logger.info("Event successfully processed: " + event);
  }
}
