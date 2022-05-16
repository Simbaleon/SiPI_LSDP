package com.example.insurance.data.requestdto;

import com.example.insurance.data.enumerations.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Mark order as ready input dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class ChangeOrderStatusInputDTO {

    private Long orderId;

    private OrderStatus status;

}
