package br.com.estudo.microservice.orderservice.services.impl;

import br.com.estudo.microservice.orderservice.entities.Order;
import br.com.estudo.microservice.orderservice.mapper.OrderMapper;
import br.com.estudo.microservice.orderservice.repositories.OrderRepository;
import br.com.estudo.microservice.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private Order getById(final Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", type: " + Order.class.getSimpleName())
                );
    }

    @Override
    public void save(CreateOrderRequest request) {
        orderRepository.save(orderMapper.fromRequest(request));
    }

    @Override
    public OrderResponse findById(final Long id) {
        return orderMapper.fromEntity(getById(id));
    }

    @Override
    public OrderResponse update(final Long id, UpdateOrderRequest request) {
        Order entity = getById(id);
        Order updated = orderMapper.fromRequest(entity, request);

        if (updated.getStatus().equals(OrderStatusEnum.CLOSED)) {
            updated.setClosedAt(now());
        }

        return orderMapper.fromEntity(orderRepository.save(updated));
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.delete(getById(id));
    }
}
