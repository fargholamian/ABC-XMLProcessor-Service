package com.tradedoubler.xmlprocessorservice.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {
  @XmlAttribute
  private Long height;
  @XmlAttribute
  private Long width;
  @XmlValue
  private String url;
}
