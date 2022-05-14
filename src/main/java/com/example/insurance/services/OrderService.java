package com.example.insurance.services;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.enumerations.OrderStatus;
import com.example.insurance.data.enumerations.OrderType;
import com.example.insurance.data.repositories.OrderRepository;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import com.example.insurance.data.responsedto.OrderDTO;
import com.example.insurance.exceptions.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void createOrder(CreateOrderInputDTO orderInputDTO, UserEntity user) {
        Order order = new Order()
                .setType(OrderType.getByUIValue(orderInputDTO.getOrderType()).get())
                .setSubject(orderInputDTO.getSubject())
                .setDescription(orderInputDTO.getDescription())
                .setDeadline(orderInputDTO.getDeadline())
                .setPrice(orderInputDTO.getPrice())
                .setAuthorUser(user)
                .setStatus(OrderStatus.WAITING_FOR_RESPONSES);
        orderRepository.save(order);
    }

    /**
     * Gets all orders.
     *
     * @param pageable the pageable
     * @return the all orders
     */
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAllByStatus(pageable, OrderStatus.WAITING_FOR_RESPONSES).map(OrderDTO::copyEntityToDTO);
    }

    /**
     * Gets orders by user id.
     *
     * @param email the email
     * @return the orders by user id
     */
    public Map<String, List<OrderDTO>> getOrdersByUsername(String email) {
        Map<String, List<OrderDTO>> orderMap = new HashMap<>();
        List<OrderDTO> executorOrdersList = orderRepository.findAllByExecutorUserEmailAndStatusIsNot(email, OrderStatus.COMPLETED)
                .stream().map(OrderDTO::copyEntityToDTO).collect(Collectors.toList());
        List<OrderDTO> authorOrderList = orderRepository.findAllByAuthorUserEmailAndStatusIsNot(email, OrderStatus.COMPLETED)
                        .stream().map(OrderDTO::copyEntityToDTO).collect(Collectors.toList());
        orderMap.put("executor", executorOrdersList);
        orderMap.put("author", authorOrderList);
        return orderMap;
    }

    /**
     * Respond to order.
     *
     * @param id   the id
     * @param user the user
     */
    public void respondToOrder(Long id, UserEntity user) {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        order.getExecutorsResponses().add(user);
        orderRepository.save(order);
    }
}
