package br.com.estudo.microservice.orderservice.services;

import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

import java.util.List;

public interface OrderService {

    void save(CreateOrderRequest request);

    OrderResponse findById(final Long id);

    OrderResponse update(final Long id, UpdateOrderRequest request);

    void deleteById(Long id);

    List<OrderResponse> findAll();
}
