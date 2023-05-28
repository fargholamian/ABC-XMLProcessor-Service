package com.tradedoubler.xmlprocessorservice.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Category {
  @XmlAttribute
  private Long id;
  @XmlAttribute
  private String name;
  @XmlAttribute
  private String tdCategoryName;
}
