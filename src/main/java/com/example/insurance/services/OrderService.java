package com.example.insurance.services;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.enumerations.OrderStatus;
import com.example.insurance.data.enumerations.OrderType;
import com.example.insurance.data.repositories.OrderRepository;
import com.example.insurance.data.requestdto.AssignUserToOrderInputDTO;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import com.example.insurance.data.requestdto.ChangeOrderStatusInputDTO;
import com.example.insurance.data.responsedto.OrderDTO;
import com.example.insurance.data.responsedto.UserDTO;
import com.example.insurance.exceptions.OrderNotFoundException;
import com.example.insurance.exceptions.OrderStatusNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        List<OrderDTO> executorOrdersList = orderRepository.findAllByExecutorUserEmail(email).stream()
                .filter(o -> !(o.getStatus().equals(OrderStatus.COMPLETED) || o.getStatus().equals(OrderStatus.DELETED)))
                .map(OrderDTO::copyEntityToDTO).collect(Collectors.toList());
        List<OrderDTO> authorOrderList = orderRepository.findAllByAuthorUserEmail(email).stream()
                .filter(o -> !(o.getStatus().equals(OrderStatus.COMPLETED) || o.getStatus().equals(OrderStatus.DELETED)))
                .map(OrderDTO::copyEntityToDTO).collect(Collectors.toList());
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

    /**
     * Delete order.
     *
     * @param id the id
     */
    public void  deleteOrder(Long id) {
        orderRepository.findById(id).map(order -> order.setStatus(OrderStatus.DELETED))
                .map(orderRepository::save).orElseThrow(OrderNotFoundException::new);
    }

    /**
     * Gets order by id.
     *
     * @param id the id
     * @return the order by id
     */
    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id).map(OrderDTO::copyEntityToDTO).orElseThrow(OrderNotFoundException::new);
    }

    /**
     * Gets all responses for order.
     *
     * @param id the id
     * @return the all responses for order
     */
    public List<UserDTO> getAllResponsesForOrder(Long id) {
        Set<UserEntity> orderResponses = orderRepository.findById(id).map(Order::getExecutorsResponses).orElseThrow(OrderNotFoundException::new);
        return orderResponses.stream().map(UserDTO::copyEntityToDTO).collect(Collectors.toList());
    }

    /**
     * Assign user to order.
     *
     * @param inputDTO the input dto
     */
    public void assignUserToOrder(AssignUserToOrderInputDTO inputDTO) {
        UserEntity user = userService.getUserById(inputDTO.getUserId());
        Order order = orderRepository.findById(inputDTO.getOrderId()).orElseThrow(OrderNotFoundException::new);
        order.setExecutorUser(user).setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
    }

    /**
     * Mark order as ready to check.
     *
     * @param inputDTO the input dto
     */
    public void changeOrderStatus(ChangeOrderStatusInputDTO inputDTO) {
        Order order = orderRepository.findById(inputDTO.getOrderId()).orElseThrow(OrderNotFoundException::new);
        order.setStatus(inputDTO.getStatus());
        orderRepository.save(order);
    }
}
