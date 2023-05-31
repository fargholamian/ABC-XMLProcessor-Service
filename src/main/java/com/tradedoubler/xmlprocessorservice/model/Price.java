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
import lombok.Getter;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "prices")
public class Price {
  @Id
  @XmlTransient
  private UUID id = UUID.randomUUID();
  @XmlAttribute
  private String currency;
  @XmlAttribute
  private Long date;
  @XmlAttribute
  private String dateFormat;
  @XmlValue
  private Double value;
}
