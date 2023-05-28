package com.tradedoubler.xmlprocessorservice.controller;

import com.tradedoubler.xmlprocessorservice.model.File;
import com.tradedoubler.xmlprocessorservice.repo.FileRepository;
import com.tradedoubler.xmlprocessorservice.service.XmlParserService;
import jakarta.xml.bind.JAXBException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.stream.XMLStreamException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileProcessorController {

  Logger logger = LoggerFactory.getLogger(FileProcessorController.class);

  @Value("${application.directories.uploaded}")
  private String uploadedFileDirectory;

  private final FileRepository fileRepository;

  private final XmlParserService xmlParserService;

  @PostMapping("/received")
  public ResponseEntity<String> uploadFile(@RequestBody File file) throws XMLStreamException, JAXBException {

    logger.info("User id:" + file.getUserId());
    Path path = Paths.get(uploadedFileDirectory + "/" + file.getName());
    if (Files.notExists(path)) {
      return ResponseEntity.badRequest().body("Can't find the uploaded file");
    }

    fileRepository.save(file);
    xmlParserService.parseAndStore(path.toString());

    String result = "Uploaded file registered successfully!";
    return ResponseEntity.ok(result);
  }
}