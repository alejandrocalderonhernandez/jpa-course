package com.debuggeandoideas.gadgetplus.repositories;

import com.debuggeandoideas.gadgetplus.entities.RejectProductEntity;
import com.debuggeandoideas.gadgetplus.entities.RejectProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectProductRepository extends JpaRepository<RejectProductEntity, RejectProductId> {
}
