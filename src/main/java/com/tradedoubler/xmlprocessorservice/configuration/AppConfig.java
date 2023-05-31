package com.tradedoubler.xmlprocessorservice.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

  @Value("${application.directories.uploaded}")
  private String uploadedFileDirectory;

  @Value("${application.authentication.uri}")
  private String getUserUri;
}
