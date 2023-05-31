package com.tradedoubler.xmlprocessorservice.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
  private LocalDateTime timestamp;
  private HttpStatus status;
  private String message;
}
