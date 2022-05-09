package com.example.insurance.data.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    WAITING_FOR_RESPONSES("В ожидании откликов"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Выполнен");

    private String uiValue;

}
