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
@Table(name = "images")
public class Image {
  @Id
  @XmlTransient
  private UUID id = UUID.randomUUID();
  @XmlAttribute
  private Long height;
  @XmlAttribute
  private Long width;
  @XmlValue
  private String url;
}
