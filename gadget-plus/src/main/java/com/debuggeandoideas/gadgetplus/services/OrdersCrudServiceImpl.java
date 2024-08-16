package com.debuggeandoideas.gadgetplus.services;

import com.debuggeandoideas.gadgetplus.dtos.OrderDTO;
import com.debuggeandoideas.gadgetplus.dtos.ProductsDTO;
import com.debuggeandoideas.gadgetplus.entities.OrderEntity;
import com.debuggeandoideas.gadgetplus.entities.ProductEntity;
import com.debuggeandoideas.gadgetplus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersCrudServiceImpl implements OrdersCrudService{

    private final OrderRepository orderRepository;

    @Override
    public String create(OrderDTO order) {
        return "";
    }

    @Override
    public OrderDTO read(Long id) {
        return this.mapOrderFromEntity(this.orderRepository.findById(id).orElseThrow());
    }

    @Override
    public OrderDTO update(OrderDTO order, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private OrderDTO mapOrderFromEntity(OrderEntity orderEntity) {
        final var modelMapper = new ModelMapper();

        modelMapper
                .typeMap(ProductEntity.class, ProductsDTO.class)
                .addMappings(mapper -> mapper.map(
                        entity -> entity.getCatalog().getName(), ProductsDTO::setName
                ));

        return modelMapper.map(orderEntity, OrderDTO.class);
    }
}
