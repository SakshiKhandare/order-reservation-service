package com.platform.ordering.repository;

import com.platform.ordering.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    @Query("""
    SELECT DISTINCT o
    FROM Order o
    LEFT JOIN FETCH o.items i
    LEFT JOIN FETCH i.product
    WHERE o.orderNumber = :orderNumber
""")
    Optional<Order> findByOrderNumberWithItems(String orderNumber);

}
