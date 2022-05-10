package com.example.insurance.data.repositories;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.enumerations.OrderStatus;
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


    /**
     * Find all by author user id and status is not list.
     *
     * @param email       the email
     * @param orderStatus the order status
     * @return the list
     */
    List<Order> findAllByAuthorUserEmailAndStatusIsNot(@NonNull String email, @NonNull OrderStatus orderStatus);

    /**
     * Find all by executor user id and status is not list.
     *
     * @param email       the email
     * @param orderStatus the order status
     * @return the list
     */
    List<Order> findAllByExecutorUserEmailAndStatusIsNot(@NonNull String email, @NonNull OrderStatus orderStatus);

    /**
     * Find all by status list.
     *
     * @param orderStatus the order status
     * @return the list
     */
    List<Order> findAllByStatus(@NonNull OrderStatus orderStatus);
}
