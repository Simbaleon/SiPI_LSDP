package com.example.insurance.data.repositories;

import com.example.insurance.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
