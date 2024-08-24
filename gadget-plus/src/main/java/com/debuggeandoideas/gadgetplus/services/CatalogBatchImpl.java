package com.debuggeandoideas.gadgetplus.services;

import com.debuggeandoideas.gadgetplus.entities.ProductCatalogEntity;
import com.debuggeandoideas.gadgetplus.repositories.ProductCatalogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogBatchImpl implements CatalogBatch {

    private final ProductCatalogRepository productCatalogRepository;


    @Override
    public void insertBatch() {
        log.info("Inserting batch");
        final var start = System.currentTimeMillis();
        this.productCatalogRepository.saveAllAndFlush(products);
        final var end = System.currentTimeMillis();
        log.info("Inserting batch successful in {} ms", end - start);
    }

    @Override
    public void deleteBatch() {
        log.info("Delete batch");
        final var ids = products.stream().map(ProductCatalogEntity::getId).toList();
        final var start = System.currentTimeMillis();
        this.productCatalogRepository.deleteAllByIdInBatch(ids);
        final var end = System.currentTimeMillis();
        log.info("Delete batch successful in {} ms", end - start);
    }



    private static List<ProductCatalogEntity> products;

    static {
        products = List.of(
                ProductCatalogEntity
                        .builder()
                        .name("portable monitor S")
                        .brand("LG")
                        .price(BigDecimal.valueOf(123.44))
                        .launchingDate(LocalDate.now())
                        .isDiscount(false)
                        .description("New product")
                        .rating(Short.valueOf("0"))
                        .build(),
                ProductCatalogEntity
                        .builder()
                        .name("portable monitor M")
                        .brand("LG")
                        .price(BigDecimal.valueOf(133.44))
                        .launchingDate(LocalDate.now())
                        .isDiscount(false)
                        .description("New product")
                        .rating(Short.valueOf("0"))
                        .build(),
                ProductCatalogEntity
                        .builder()
                        .name("portable monitor L")
                        .brand("LG")
                        .price(BigDecimal.valueOf(143.44))
                        .launchingDate(LocalDate.now())
                        .isDiscount(false)
                        .description("New product")
                        .rating(Short.valueOf("0"))
                        .build(),
                ProductCatalogEntity
                        .builder()
                        .name("portable monitor XL")
                        .brand("LG")
                        .price(BigDecimal.valueOf(1253.44))
                        .launchingDate(LocalDate.now())
                        .isDiscount(false)
                        .description("New product")
                        .rating(Short.valueOf("0"))
                        .build(),
                ProductCatalogEntity
                        .builder()
                        .name("portable monitor Dual")
                        .brand("LG")
                        .price(BigDecimal.valueOf(2253.44))
                        .launchingDate(LocalDate.now())
                        .isDiscount(false)
                        .description("New product")
                        .rating(Short.valueOf("0"))
                        .build()
        );
    }
}
