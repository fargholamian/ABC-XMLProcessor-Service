package com.tradedoubler.xmlprocessorservice.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
  @XmlAttribute
  private String groupingId;

  @XmlAttribute
  private String language;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String name;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String description;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private Image productImage;

  @XmlElementWrapper(name = "categories", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @XmlElement(name = "category", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private List<Category> categories;

  @XmlElementWrapper(name = "fields", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @XmlElement(name = "field", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private List<Field> fields;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String weight;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String size;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String model;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String brand;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String manufacturer;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String techSpecs;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String shortDescription;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String promoText;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String ean;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String upc;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String isbn;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String mpn;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String sku;

  @XmlElementWrapper(name = "offers", namespace = "urn:com:tradedoubler:pf:model:xml:output")
  @XmlElement(name = "offer", namespace = "urn:com:tradedoubler:pf:model:xml:output")
  private List<Offer> offers;
}
