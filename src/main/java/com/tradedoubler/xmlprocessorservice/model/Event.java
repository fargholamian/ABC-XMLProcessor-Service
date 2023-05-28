package com.tradedoubler.xmlprocessorservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events", uniqueConstraints = { @UniqueConstraint(name = "UniqueFileNameForEachUser", columnNames = { "userId", "filename" }) })
public class Event {
  @Id
  private UUID id;
  private UUID userId;
  private String filename;
}
