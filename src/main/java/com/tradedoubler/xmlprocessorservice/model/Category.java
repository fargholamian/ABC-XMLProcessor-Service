package com.tradedoubler.xmlprocessorservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.UUID;
import lombok.Getter;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "Categories")
public class Category {
  @Id
  private UUID id = UUID.randomUUID();

  @XmlAttribute(name="id")
  private Long categoryId;
  @XmlAttribute
  private String name;
  @XmlAttribute
  private String tdCategoryName;
}
