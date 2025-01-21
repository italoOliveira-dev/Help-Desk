package br.com.estudo.microservice.orderservice.services;

import models.requests.CreateOrderRequest;

public interface OrderService {

    void save(CreateOrderRequest request);
}
