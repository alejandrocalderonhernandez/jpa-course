package com.debuggeandoideas.gadgetplus.repositories;

import com.debuggeandoideas.gadgetplus.entities.ProductCatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {
}
