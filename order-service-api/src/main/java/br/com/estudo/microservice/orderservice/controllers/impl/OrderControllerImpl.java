package br.com.estudo.microservice.orderservice.controllers.impl;

import br.com.estudo.microservice.orderservice.controllers.OrderController;
import br.com.estudo.microservice.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
}
