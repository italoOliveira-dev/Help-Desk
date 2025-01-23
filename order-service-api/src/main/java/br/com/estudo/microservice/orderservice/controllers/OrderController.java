package br.com.estudo.microservice.orderservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Controller", description = "Controller responsible for orders operations")
@RequestMapping("/api/orders")
public interface OrderController {

    @Operation(summary = "Save new order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Order created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )
    })
    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody CreateOrderRequest request);

    @Operation(description = "Update order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order update success",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(@Parameter(description = "Order id", required = true, example = "10")
                                         @PathVariable
                                         Long id,
                                         @Valid
                                         @RequestBody
                                         UpdateOrderRequest request);

    @Operation(description = "Find order by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@Parameter(description = "Order id", required = true, example = "10")
                                           @NotNull(message = "The order id must be informed")
                                           @PathVariable
                                           final Long id);

    @Operation(description = "Find all orders")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class))
                    )
            )
    })
    @GetMapping
    ResponseEntity<List<OrderResponse>> findAll();

    @Operation(description = "Delete order by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "No content"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@Parameter(description = "Order id", required = true, example = "10")
                                    @NotNull(message = "The order id must be informed")
                                    @PathVariable
                                    final Long id);
}
