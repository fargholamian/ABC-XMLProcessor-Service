package com.tradedoubler.xmlprocessorservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.UUID;
import lombok.Getter;

@Getter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "products")
public class Product {
  @Id
  @XmlTransient
  @JsonIgnore
  private UUID id = UUID.randomUUID();

  @ManyToOne
  @XmlElement(name = "owner")
  @JsonProperty("owner")
  private Event event;

  @XmlAttribute
  private String groupingId;

  @XmlAttribute
  private String language;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String name;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String description;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @OneToOne(cascade = CascadeType.ALL)
  private Image productImage;

  @XmlElementWrapper(name = "categories", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @XmlElement(name = "category", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @OneToMany(cascade = CascadeType.ALL)
  private List<Category> categories;

  @XmlElementWrapper(name = "fields", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @XmlElement(name = "field", namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @OneToMany(cascade = CascadeType.ALL)
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
  @OneToMany(cascade = CascadeType.ALL)
  private List<Offer> offers;

  public void setEvent(Event event) {
    this.event = event;
  }
}
