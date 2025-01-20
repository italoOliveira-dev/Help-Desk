package br.com.estudo.microservice.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import models.enums.OrderStatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static models.enums.OrderStatusEnum.OPEN;

@Entity(name = "tb_orders")
@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String requestId;

    @Column(nullable = false, length = 45)
    private String customerId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status = OPEN;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
