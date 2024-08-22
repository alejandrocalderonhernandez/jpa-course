package com.debuggeandoideas.gadgetplus.controllers;

import com.debuggeandoideas.gadgetplus.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Map<String, String>> startTransaction(@RequestParam Long id) {
        this.transactionService.executeTransaction(id);
        return ResponseEntity.ok( Map.of("Transaction", "OK"));
    }
}
