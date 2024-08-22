package com.debuggeandoideas.gadgetplus.controllers;


import com.debuggeandoideas.gadgetplus.dtos.OrderDTO;
import com.debuggeandoideas.gadgetplus.services.OrdersCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "order")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersCrudService ordersCrudService;

    @GetMapping(path = "{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(ordersCrudService.read(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDTO orderDTO) {
        var path = "/" + this.ordersCrudService.create(orderDTO);
        return ResponseEntity.created(URI.create(path)).build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(this.ordersCrudService.update(orderDTO, id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.ordersCrudService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        this.ordersCrudService.delete(name);
        return ResponseEntity.noContent().build();
    }
}
