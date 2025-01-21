package br.com.estudo.microservice.orderservice.controllers.impl;

import br.com.estudo.microservice.orderservice.controllers.OrderController;
import br.com.estudo.microservice.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
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
}
