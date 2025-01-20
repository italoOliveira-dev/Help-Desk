package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter
public enum OrderStatusEnum {

    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    CLOSED("Closed"),
    CANCELED("Canceled");

    private final String status;

    public static OrderStatusEnum toEnum(final String status) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(orderStatusEnum -> orderStatusEnum.getStatus().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid order status " + status));
    }

}
