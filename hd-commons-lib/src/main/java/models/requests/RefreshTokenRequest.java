package models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(@NotBlank(message = "Refresh token is required")
                                  @Size(min = 16, max = 50, message = "Refresh token must be between 16 an 50 characters")
                                  String refreshToken) {
}
