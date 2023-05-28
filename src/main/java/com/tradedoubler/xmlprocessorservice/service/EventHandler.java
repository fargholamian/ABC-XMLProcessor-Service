package com.tradedoubler.xmlprocessorservice.service;

import com.tradedoubler.xmlprocessorservice.model.Event;

public interface EventHandler {
  void handle(Event event);
}
