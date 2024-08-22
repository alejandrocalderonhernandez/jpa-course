package com.debuggeandoideas.gadgetplus.services;

import com.debuggeandoideas.gadgetplus.repositories.BillRepository;
import com.debuggeandoideas.gadgetplus.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final OrderRepository orderRepository;
    private final BillRepository billRepository;



    @Transactional
    @Override
    public void executeTransaction(Long id) {
        log.info("TRANSACTION ACTIVE 1 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 1 {}", TransactionSynchronizationManager.getCurrentTransactionName());

        this.updateOrder(id);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateOrder(Long id) {
        log.info("TRANSACTION ACTIVE 2 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 2 {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final var order = orderRepository.findById(id).orElseThrow();
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        this.validProducts(id);
        this.updateBill(order.getBill().getId());
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateBill(String id) {
        log.info("TRANSACTION ACTIVE 4 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 4 {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final var bill = billRepository.findById(id).orElseThrow();
        bill.setClientRfc("123");
        billRepository.save(bill);

    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void validProducts(Long id) {
        log.info("TRANSACTION ACTIVE 3 {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TRANSACTION NAME 3 {}", TransactionSynchronizationManager.getCurrentTransactionName());
        final var order = orderRepository.findById(id).orElseThrow();
        if (order.getProducts().isEmpty()) {
            throw new IllegalStateException("There are no products in the order");
        }
    }
}
