package com.example.insurance.data.repositories;

import com.example.insurance.data.entities.Order;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * The interface Order repository.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all by author user id list.
     *
     * @param id the id
     * @return the list
     */
    List<Order> findAllByAuthorUserId(@NonNull Long id);

    /**
     * Find all by executor user id list.
     *
     * @param id the id
     * @return the list
     */
    List<Order> findAllByExecutorUserId(@NonNull Long id);
}
