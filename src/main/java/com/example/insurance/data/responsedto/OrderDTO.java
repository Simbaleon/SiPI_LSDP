package com.example.insurance.data.responsedto;

import com.example.insurance.data.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private String subject;

    private String type;

    private String deadline;

    private Double price;

    private String status;

    static public OrderDTO copyEntityToDTO(Order order) {
        OrderDTO dto = new OrderDTO(
                order.getId(),
                order.getSubject(),
                order.getType().getUiValue(),
                order.getDeadline().getDayOfMonth() + "-" + order.getDeadline().getMonthValue() + "-" + order.getDeadline().getYear(),
                order.getPrice(),
                order.getStatus().getUiValue());
        return dto;
    }
}
