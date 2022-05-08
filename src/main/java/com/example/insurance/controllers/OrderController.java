package com.example.insurance.controllers;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.enumerations.OrderType;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import com.example.insurance.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
     * @return the response entity
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderInputDTO order, @AuthenticationPrincipal UserEntity user) {
        orderService.createOrder(order);
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
     * @return the all orders
     */
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Gets orders by user id.
     *
     * @param id the id
     * @return the orders by user id
     */
    @GetMapping("/getOrdersByUserId")
    public ResponseEntity<Map<String, List<Order>>> getOrdersByUserId(@RequestParam Long id) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(id));
    }

}
