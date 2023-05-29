package com.tradedoubler.xmlprocessorservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.UUID;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "offers")
public class Offer {
  @Id
  @XmlTransient
  private UUID id = UUID.randomUUID();
  @XmlAttribute(name = "id")
  private String offerId;
  @XmlAttribute
  private String sourceProductId;
  @XmlAttribute
  private String modifiedDate;
  @XmlAttribute
  private String dateFormat;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private Long feedId;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String productUrl;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String programName;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String programLogo;

  @XmlElementWrapper(name = "priceHistory",namespace = "urn:com:tradedoubler:pf:model:xml:output")
  @XmlElement(name = "price" , namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @OneToMany(cascade = CascadeType.ALL)
  private List<Price> priceHistory;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String warranty;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private Long inStock;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String availability;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String deliveryTime;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  @Column(name = "condittion")
  private String condition;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String shippingCost;
}
