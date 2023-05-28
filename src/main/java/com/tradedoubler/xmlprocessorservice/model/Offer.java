package com.tradedoubler.xmlprocessorservice.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Offer {
  @XmlAttribute
  private String id;
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
  private String condition;

  @XmlElement(namespace = "urn:com:tradedoubler:pf:model:xml:common")
  private String shippingCost;
}
