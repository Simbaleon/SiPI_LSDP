package com.example.insurance.services;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.repositories.OrderRepository;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(CreateOrderInputDTO orderInputDTO) {
        Order order = new Order();
//        order.setSubject(orderInputDTO.getSubject());
//        order.setDescription(orderInputDTO.getDescription());
//        order.setType(orderInputDTO.getOrderType());
//        order.setDeadline(orderInputDTO.getDeadline());
//        order.setPrice(orderInputDTO.getPrice());
        orderRepository.save(order);
    }
}
