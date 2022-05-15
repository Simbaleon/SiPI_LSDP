package com.example.insurance.data.repositories;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.enumerations.OrderStatus;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


/**
 * The interface Order repository.
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

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
     * @param email the email
     * @return the list
     */
    List<Order> findAllByAuthorUserEmail(@NonNull String email);

    /**
     * Find all by executor user id and status is not list.
     *
     * @param email the email
     * @return the list
     */
    List<Order> findAllByExecutorUserEmail(@NonNull String email);

    /**
     * Find all by status list.
     *
     * @param pageable    the pageable
     * @param orderStatus the order status
     * @return the list
     */
    Page<Order> findAllByStatus(@NonNull Pageable pageable, @NonNull OrderStatus orderStatus);
}
