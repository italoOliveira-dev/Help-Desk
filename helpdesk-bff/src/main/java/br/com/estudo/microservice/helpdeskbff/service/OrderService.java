package br.com.estudo.microservice.helpdeskbff.service;


import br.com.estudo.microservice.helpdeskbff.client.OrderFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderFeignClient orderFeignClient;

    public void save(CreateOrderRequest request) {
        orderFeignClient.save(request);
    }

    public OrderResponse findById(final Long id) {
        return orderFeignClient.findById(id).getBody();
    }



    public OrderResponse update(final Long id, UpdateOrderRequest request) {
        return orderFeignClient.update(id, request).getBody();
    }

    public void deleteById(Long id) {
        orderFeignClient.deleteById(id);
    }

    public List<OrderResponse> findAll() {
        return orderFeignClient.findAll().getBody();
    }

    public Page<OrderResponse> findAllPageable(PageRequest pageRequest) {

        String sort = pageRequest.getSort().isSorted() ?
                pageRequest.getSort().iterator().next().getProperty() :
                null;
        String direction = pageRequest.getSort().isSorted() ?
                pageRequest.getSort().iterator().next().getDirection().name() :
                "ASC";

        return orderFeignClient.findAllPageable(pageRequest.getPageNumber(),
                                                pageRequest.getPageSize(),
                                                sort,
                                                direction).getBody();
    }
}
