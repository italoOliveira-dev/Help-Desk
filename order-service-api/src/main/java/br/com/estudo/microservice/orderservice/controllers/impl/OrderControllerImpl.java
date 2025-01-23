package br.com.estudo.microservice.orderservice.controllers.impl;

import br.com.estudo.microservice.orderservice.controllers.OrderController;
import br.com.estudo.microservice.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {

        orderService.save(request);

        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok(orderService.update(id, request));
    }

    @Override
    public ResponseEntity<OrderResponse> findById(final Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @Override
    public ResponseEntity<Void> deleteById(final Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> findAllPageable(Integer page, Integer size, String sort, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);

        return ResponseEntity.ok(orderService.findAllPageable(pageRequest));
    }

}
