package com.tradedoubler.xmlprocessorservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tradedoubler.xmlprocessorservice.BaseServiceTest;
import com.tradedoubler.xmlprocessorservice.configuration.AppConfig;
import com.tradedoubler.xmlprocessorservice.model.Event;
import com.tradedoubler.xmlprocessorservice.model.Product;
import com.tradedoubler.xmlprocessorservice.repo.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class XmlUploadEventHandlerTest extends BaseServiceTest {
  @Mock
  ProductRepository productRepository;

  @Mock
  private AppConfig appConfig;

  @InjectMocks
  private XmlUploadEventHandler xmlUploadEventHandler;

  @Test
  public void shouldHandleSuccessfully_WhenXmlFileExistsAndCorrect() {
    when(appConfig.getUploadedFileDirectory()).thenReturn("src/test/resources/files");

//    when(productRepository.save(any(Product.class))).thenReturn(mock(Product.class));
    Event event = new Event(UUID.randomUUID(), UUID.randomUUID(), "Products.xml");

    ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
    xmlUploadEventHandler.handle(event);

    verify(productRepository, times(3)).save(argument.capture());
    List<Product> products = argument.getAllValues();

    assertThat(products.get(0).getGroupingId()).isEqualTo("skuSKU1-TV000001");
    assertThat(products.get(0).getLanguage()).isEqualTo("sv");
    assertThat(products.get(0).getName()).isEqualTo("First Test product name");
    assertThat(products.get(0).getDescription()).isEqualTo("First description");
    assertThat(products.get(0).getProductImage().getHeight()).isEqualTo(60);
    assertThat(products.get(0).getProductImage().getWidth()).isEqualTo(120);
    assertThat(products.get(0).getProductImage().getUrl()).isEqualTo("https://picsum.photos/id/1/200/300");
    assertThat(products.get(0).getCategories().size()).isEqualTo(2);
    assertThat(products.get(0).getCategories().get(0).getName()).isEqualTo("Television");
    assertThat(products.get(0).getCategories().get(0).getCategoryId()).isEqualTo(65);
    assertThat(products.get(0).getCategories().get(0).getTdCategoryName()).isEqualTo("Televisions");
    assertThat(products.get(0).getFields().size()).isEqualTo(3);
    assertThat(products.get(0).getFields().get(0).getName()).isEqualTo("Company");
    assertThat(products.get(0).getFields().get(0).getValue()).isEqualTo("Sharp");
    assertThat(products.get(0).getWeight()).isEqualTo("1999 g");
    assertThat(products.get(0).getSize()).isEqualTo("110x60x60 cm");
    assertThat(products.get(0).getModel()).isEqualTo("KajSharpTV000001");
    assertThat(products.get(0).getBrand()).isEqualTo("Sharp");
    assertThat(products.get(0).getManufacturer()).isEqualTo("Sharp");
    assertThat(products.get(0).getTechSpecs()).isEqualTo("SMART HUB");
    assertThat(products.get(0).getTechSpecs()).isEqualTo("SMART HUB");
    assertThat(products.get(0).getShortDescription()).isEqualTo("Televisions from Sharp");
    assertThat(products.get(0).getPromoText()).isEqualTo("Discounted price!!");
    assertThat(products.get(0).getEan()).isEqualTo("ean10100000000000001");
    assertThat(products.get(0).getUpc()).isEqualTo("upc10100000000000001");
    assertThat(products.get(0).getIsbn()).isEqualTo("isn10100000000000001");
    assertThat(products.get(0).getMpn()).isEqualTo("mpn10100000000000001");
    assertThat(products.get(0).getSku()).isEqualTo("SKU1-TV000001");
    assertThat(products.get(0).getOffers().size()).isEqualTo(1);
    assertThat(products.get(0).getOffers().get(0).getOfferId()).isEqualTo("3d2a9e19-7d23-42ca-af66-91164f681f2a");
    assertThat(products.get(0).getOffers().get(0).getFeedId()).isEqualTo(99999999);
    assertThat(products.get(0).getOffers().get(0).getPriceHistory().size()).isEqualTo(1);
    assertThat(products.get(0).getOffers().get(0).getPriceHistory().get(0).getValue()).isEqualTo(115.5);
    // Should check other values ...

  }
}
