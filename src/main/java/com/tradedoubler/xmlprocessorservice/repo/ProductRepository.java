package com.tradedoubler.xmlprocessorservice.repo;

import com.tradedoubler.xmlprocessorservice.model.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  List<Product> findByEvent_UserId(UUID userId, Pageable pageable);
}
