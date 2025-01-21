package models.responses;

public record OrderResponse(Long id,
                            String requestId,
                            String customerId,
                            String title,
                            String description,
                            String status,
                            String createdAt,
                            String closedAt) {
}
