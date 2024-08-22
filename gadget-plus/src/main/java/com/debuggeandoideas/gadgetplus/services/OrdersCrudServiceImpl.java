package com.debuggeandoideas.gadgetplus.services;

import com.debuggeandoideas.gadgetplus.dtos.BillDTO;
import com.debuggeandoideas.gadgetplus.dtos.OrderDTO;
import com.debuggeandoideas.gadgetplus.dtos.ProductsDTO;
import com.debuggeandoideas.gadgetplus.entities.BillEntity;
import com.debuggeandoideas.gadgetplus.entities.OrderEntity;
import com.debuggeandoideas.gadgetplus.entities.ProductEntity;
import com.debuggeandoideas.gadgetplus.repositories.OrderRepository;
import com.debuggeandoideas.gadgetplus.repositories.ProductCatalogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrdersCrudServiceImpl implements OrdersCrudService{

    private final OrderRepository orderRepository;
    private final ProductCatalogRepository productCatalogRepository;

    @Override
    public String create(OrderDTO order) {
        final var toInsert = this.mapOrderFromDto(order);

        return this.orderRepository.save(toInsert).getId().toString();

    }

    @Override
    public OrderDTO read(Long id) {
        return this.mapOrderFromEntity(this.orderRepository.findById(id).orElseThrow());
    }

    @Override
    public OrderDTO update(OrderDTO order, Long id) {
            final var toUpdate = this.orderRepository.findById(id).orElseThrow();

            toUpdate.setClientName(order.getClientName());
            toUpdate.getBill().setClientRfc(order.getBill().getClientRfc());

            return this.mapOrderFromEntity(this.orderRepository.save(toUpdate));
    }

    @Override
    public void delete(Long id) {

        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Client not exist");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(String clientName) {
        if (orderRepository.existsByClientName(clientName)) {
            orderRepository.deleteByClientName(clientName);
        } else {
            throw new IllegalArgumentException("Client not exist");
        }
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

    private OrderEntity mapOrderFromDto(OrderDTO orderDTO) {

        final var orderResponse = new OrderEntity();
        final var modelMapper = new ModelMapper();

        modelMapper
                .typeMap(BillDTO.class, BillEntity.class)
                        .addMappings(mapper -> mapper.map(
                                BillDTO::getIdBill, BillEntity::setId));


        log.info("Before {}", orderResponse);
        modelMapper.map(orderDTO, orderResponse);


        log.info("After {}", orderResponse);

         final var total = this.getAndSetProductsAndTotal(orderDTO.getProducts(), orderResponse);

        log.info("After with products {}", orderResponse);

        orderResponse.getBill().setTotalAmount(total);

        return orderResponse;
    }




    private BigDecimal getAndSetProductsAndTotal(List<ProductsDTO> productsDto, OrderEntity orderEntity) {

        var total = new AtomicReference<>(BigDecimal.ZERO);

        productsDto.forEach(product -> {
            final var productFromCatalog = this.productCatalogRepository.findByName(product.getName()).orElseThrow();

            total.updateAndGet(bigDecimal -> bigDecimal.add(productFromCatalog.getPrice()));

            final var productEntity = ProductEntity
                    .builder()
                    .quantity(product.getQuantity())
                    .catalog(productFromCatalog)
                    .build();

            orderEntity.addProduct(productEntity);
            productEntity.setOrder(orderEntity);

        });

        return total.get();

    }




}
