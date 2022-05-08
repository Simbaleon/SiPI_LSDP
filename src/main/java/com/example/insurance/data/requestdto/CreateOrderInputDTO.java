package com.example.insurance.data.requestdto;

import com.example.insurance.data.enumerations.OrderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderInputDTO {

    private String subject;

    private String description;

    private String orderType;

    private LocalDateTime deadline;

    private Double price;
}
