package com.debuggeandoideas.gadgetplus.repositories;

import com.debuggeandoideas.gadgetplus.entities.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    @Modifying
    void deleteByClientName(String clientName);

    Boolean existsByClientName(String clientName);
}
