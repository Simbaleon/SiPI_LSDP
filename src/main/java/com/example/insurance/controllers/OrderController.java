package com.example.insurance.controllers;

import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.enumerations.OrderType;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import com.example.insurance.data.requestdto.RespondToOrderInputDTO;
import com.example.insurance.data.responsedto.OrderDTO;
import com.example.insurance.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Order controller.
 */
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Create order response entity.
     *
     * @param order the order
     * @param user  the user
     * @return the response entity
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderInputDTO order, @AuthenticationPrincipal UserEntity user) {
        orderService.createOrder(order, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Gets order types.
     *
     * @return the order types
     */
    @GetMapping("/getTypes")
    public ResponseEntity<List<String>> getOrderTypes() {
        return ResponseEntity.ok(Arrays.stream(OrderType.values()).map(OrderType::getUiValue).collect(Collectors.toList()));
    }

    /**
     * Gets all orders.
     *
     * @param pageable the pageable
     * @return the all orders
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    /**
     * Gets orders by user id.
     *
     * @param username the username
     * @return the orders by user id
     */
    @GetMapping("/getAllByUsername")
    public ResponseEntity<Map<String, List<OrderDTO>>> getAllOrdersByUsername(@RequestParam String username) {
        return ResponseEntity.ok(orderService.getOrdersByUsername(username));
    }

    /**
     * Respond to order response entity.
     *
     * @param inputDTO the input dto
     * @param user     the user
     * @return the response entity
     */
    @PatchMapping("/respondToOrder")
    public ResponseEntity<Void> respondToOrder(@RequestBody RespondToOrderInputDTO inputDTO, @AuthenticationPrincipal UserEntity user) {
        orderService.respondToOrder(inputDTO.getId(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
