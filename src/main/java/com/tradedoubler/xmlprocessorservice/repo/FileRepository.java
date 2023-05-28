package com.tradedoubler.xmlprocessorservice.repo;

import com.tradedoubler.xmlprocessorservice.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
