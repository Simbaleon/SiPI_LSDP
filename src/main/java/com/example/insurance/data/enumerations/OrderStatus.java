package com.example.insurance.data.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    WAITING_FOR_RESPONSES("В ожидании откликов"),
    IN_PROGRESS("В процессе"),
    READY_TO_CHECK("Готов к проверке"),
    COMPLETED("Выполнен"),
    DELETED("Удалён");

    private String uiValue;

    public static Optional<OrderStatus> getByUiValue(@NonNull String uiValue) {
        return Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.getUiValue().equals(uiValue)).findFirst();
    }

    public static Optional<OrderStatus> getByName(@NonNull String name) {
        return Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.name().equals(name)).findFirst();
    }

}
