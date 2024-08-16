package com.debuggeandoideas.gadgetplus.services;

import com.debuggeandoideas.gadgetplus.dtos.DateEval;
import com.debuggeandoideas.gadgetplus.dtos.ReportProduct;
import com.debuggeandoideas.gadgetplus.entities.ProductCatalogEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductCatalogService {

    ProductCatalogEntity findById(UUID id);
    ProductCatalogEntity findByName(String name);
    List<ProductCatalogEntity> findNameLike(String key);
    List<ProductCatalogEntity> findNameBetween(BigDecimal min, BigDecimal max);
    List<ProductCatalogEntity> findByCategoryId(Long id);
    List<ProductCatalogEntity> findByLaunchingDate(LocalDate date, DateEval key);
    List<ProductCatalogEntity> findByBrandAndRating(String brand, Short rating);
    List<ReportProduct> makeReport();

    Page<ProductCatalogEntity> findAll(String field, Boolean desc, Integer page);
    Page<ProductCatalogEntity> findAllByBrand(String brand);

    Integer countByBrand(String brand);
}
