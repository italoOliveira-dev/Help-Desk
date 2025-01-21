package br.com.estudo.microservice.orderservice.services.impl;

import br.com.estudo.microservice.orderservice.mapper.OrderMapper;
import br.com.estudo.microservice.orderservice.repositories.OrderRepository;
import br.com.estudo.microservice.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void save(CreateOrderRequest request) {
        orderRepository.save(orderMapper.fromRequest(request));
    }
}
