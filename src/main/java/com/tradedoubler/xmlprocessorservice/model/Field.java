package com.tradedoubler.xmlprocessorservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlValue;
import java.util.UUID;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "fields")
public class Field {
  @Id
  @XmlTransient
  private UUID id = UUID.randomUUID();
  @XmlAttribute
  private String name;
  @XmlValue
  private String value;
}
