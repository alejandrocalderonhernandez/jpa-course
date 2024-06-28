package com.debuggeandoideas.gadgetplus.repositories;

import com.debuggeandoideas.gadgetplus.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
