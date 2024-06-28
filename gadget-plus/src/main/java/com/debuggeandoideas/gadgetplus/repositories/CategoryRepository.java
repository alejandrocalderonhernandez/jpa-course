package com.debuggeandoideas.gadgetplus.repositories;

import com.debuggeandoideas.gadgetplus.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
