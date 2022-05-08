package com.example.insurance.services;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.enumerations.OrderType;
import com.example.insurance.data.repositories.OrderRepository;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     */
    public void createOrder(CreateOrderInputDTO orderInputDTO, UserEntity user) {
        Order order = new Order()
                .setType(OrderType.getByUIValue(orderInputDTO.getOrderType()).get())
                .setSubject(orderInputDTO.getSubject())
                .setDescription(orderInputDTO.getDescription())
                .setDeadline(orderInputDTO.getDeadline())
                .setPrice(orderInputDTO.getPrice())
                .setAuthorUser(user);
        orderRepository.save(order);
    }

    /**
     * Gets all orders.
     *
     * @return the all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Gets orders by user id.
     *
     * @param id the id
     * @return the orders by user id
     */
    public Map<String, List<Order>> getOrdersByUserId(Long id) {
        Map<String, List<Order>> orderMap = new HashMap<>();
        orderMap.put("executor", orderRepository.findAllByExecutorUserId(id));
        orderMap.put("author", orderRepository.findAllByAuthorUserId(id));
        return orderMap;
    }
}
