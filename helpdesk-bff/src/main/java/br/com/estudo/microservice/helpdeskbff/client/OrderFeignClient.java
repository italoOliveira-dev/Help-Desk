package br.com.estudo.microservice.helpdeskbff.client;

import jakarta.validation.Valid;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "order-service-api",
        path = "/api/orders"
)
public interface OrderFeignClient {

    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody CreateOrderRequest request);

    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(@PathVariable
                                         Long id,
                                         @Valid
                                         @RequestBody
                                         UpdateOrderRequest request);

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable final Long id);

    @GetMapping
    ResponseEntity<List<OrderResponse>> findAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable final Long id);

    @GetMapping("/page")
    ResponseEntity<Page<OrderResponse>> findAllPageable(@RequestParam(value = "page", defaultValue = "0")
                                                        Integer page,
                                                        @RequestParam(value = "size", defaultValue = "5")
                                                        Integer size,
                                                        @RequestParam(value = "sort", required = false)
                                                        String sort,
                                                        @RequestParam(value = "direction", defaultValue = "ASC")
                                                        String direction);
}
