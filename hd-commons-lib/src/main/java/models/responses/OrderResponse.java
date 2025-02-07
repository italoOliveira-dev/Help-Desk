package models.responses;

import java.io.Serial;
import java.io.Serializable;

public record OrderResponse(Long id,
                            String requestId,
                            String customerId,
                            String title,
                            String description,
                            String status,
                            String createdAt,
                            String closedAt) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
