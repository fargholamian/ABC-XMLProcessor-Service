package com.tradedoubler.xmlprocessorservice.repo;

import com.tradedoubler.xmlprocessorservice.model.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
