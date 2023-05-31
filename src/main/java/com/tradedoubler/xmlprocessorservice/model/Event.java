package com.tradedoubler.xmlprocessorservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events", uniqueConstraints = { @UniqueConstraint(name = "UniqueFileNameForEachUser", columnNames = { "userId", "filename" }) })
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {
  @Id
  private UUID id;

  @XmlElement
  private UUID userId;

  @XmlElement
  private String filename;
}
