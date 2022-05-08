package com.example.insurance.services;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.repositories.OrderRepository;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * The type Order service.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    /**
     * Create order.
     *
     * @param orderInputDTO the order input dto
     * @param user          the user
     */
    public void createOrder(CreateOrderInputDTO orderInputDTO, User user) {
        Order order = new Order();
        order.setSubject(orderInputDTO.getSubject());
        order.setDescription(orderInputDTO.getDescription());
        order.setType(orderInputDTO.getOrderType());
        order.setDeadline(orderInputDTO.getDeadline());
        order.setPrice(orderInputDTO.getPrice());
        order.setAuthorUser(userService.getUserEntityByEmail(user.getUsername()));
        orderRepository.save(order);
    }
}
