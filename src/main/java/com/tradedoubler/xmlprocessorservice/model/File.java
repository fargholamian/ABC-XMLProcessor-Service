package com.tradedoubler.xmlprocessorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files", uniqueConstraints = { @UniqueConstraint(name = "UniqueFileNameForEachUser", columnNames = { "userId", "name" }) })
public class File {
  @Id
  @JsonIgnoreProperties
  private UUID id = UUID.randomUUID();

  private String name;

  @JsonProperty("user_id")
  private Long userId;

  @JsonIgnoreProperties
  private Boolean isProcessed = Boolean.FALSE;
}
