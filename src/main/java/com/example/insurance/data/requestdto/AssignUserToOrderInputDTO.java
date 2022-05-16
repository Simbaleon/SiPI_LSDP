package com.example.insurance.data.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignUserToOrderInputDTO {

    private Long orderId;

    private Long userId;
}
