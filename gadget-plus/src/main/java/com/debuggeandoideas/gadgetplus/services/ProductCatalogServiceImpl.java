package com.debuggeandoideas.gadgetplus.services;

import com.debuggeandoideas.gadgetplus.dtos.DateEval;
import com.debuggeandoideas.gadgetplus.dtos.ReportProduct;
import com.debuggeandoideas.gadgetplus.entities.ProductCatalogEntity;
import com.debuggeandoideas.gadgetplus.repositories.ProductCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCatalogServiceImpl implements ProductCatalogService {


    private final ProductCatalogRepository catalogRepository;

    @Override
    public ProductCatalogEntity findById(UUID id) {
        return this.catalogRepository.findById(id).orElseThrow();
    }

    @Override
    public ProductCatalogEntity findByName(String name) {
        return this.catalogRepository.findByName(name).orElseThrow();
    }

    @Override
    public List<ProductCatalogEntity> findNameLike(String key) {
        return this.catalogRepository.findByNameLike(key);
    }

    @Override
    public List<ProductCatalogEntity> findNameBetween(BigDecimal min, BigDecimal max) {
        return this.catalogRepository.findByBetweenTwoPrices(min, max);
    }

    @Override
    public List<ProductCatalogEntity> findByCategoryId(Long id) {
        return this.catalogRepository.getByCategory(id);
    }

    @Override
    public List<ProductCatalogEntity> findByLaunchingDate(LocalDate date, DateEval key) {
        if (key.equals(DateEval.AFTER)) {
            return this.catalogRepository.findByLaunchingDateAfter(date);
        } else {
            return this.catalogRepository.findByLaunchingDateBefore(date);
        }
    }

    @Override
    public List<ProductCatalogEntity> findByBrandAndRating(String brand, Short rating) {
        return this.catalogRepository.findByBrandAndRatingGreaterThan(brand, rating);
    }

    @Override
    public List<ReportProduct> makeReport() {
        return this.catalogRepository.findAndMakeReport();
    }

    @Override
    public Page<ProductCatalogEntity> findAll(String field, Boolean desc, Integer page) {
        Sort sorting = Sort.by( "name");

        if (Objects.nonNull(field)) {
            switch (field) {
                case "brand" -> sorting = Sort.by( "brand");
                case "price" -> sorting = Sort.by( "price");
                case "launchingDate" -> sorting = Sort.by( "launchingDate");
                case "rating" -> sorting = Sort.by( "rating");

                default -> throw new IllegalStateException("Unexpected value: " + field);
            }
        }


        return (desc) ?
                this.catalogRepository.findAll(PageRequest.of(page, PAGE_SIZE, sorting.descending()))
                :
                this.catalogRepository.findAll(PageRequest.of(page, PAGE_SIZE, sorting.ascending()));
    }

    @Override
    public Page<ProductCatalogEntity> findAllByBrand(String brand) {
        return null;
    }

    @Override
    public Integer countByBrand(String brand) {
        return this.catalogRepository.countTotalProductsByBrandStoreProcedure(brand);
    }

    private static final int PAGE_SIZE = 5;
}
