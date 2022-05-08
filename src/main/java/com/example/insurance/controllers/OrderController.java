package com.example.insurance.controllers;

import com.example.insurance.data.enumerations.OrderType;
import com.example.insurance.data.requestdto.CreateOrderInputDTO;
import com.example.insurance.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderInputDTO order) {
        orderService.createOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getTypes")
    public ResponseEntity<List<String>> getOrderTypes() {
        log.info("Log");
        return ResponseEntity.ok(Arrays.stream(OrderType.values()).map(OrderType::getUiValue).collect(Collectors.toList()));
    }
}
