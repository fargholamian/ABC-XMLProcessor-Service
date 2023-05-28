package com.tradedoubler.xmlprocessorservice.repo;

import com.tradedoubler.xmlprocessorservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
