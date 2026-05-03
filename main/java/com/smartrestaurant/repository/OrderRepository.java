package com.smartrestaurant.repository;

import com.smartrestaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders for a specific table
    List<Order> findByTableNo(Integer tableNo);

    // Find all pending orders for a specific table
    List<Order> findByTableNoAndStatus(Integer tableNo, String status);

    // Find any order for a table (to check if table is occupied)
    boolean existsByTableNo(Integer tableNo);

    // Find all orders by status
    List<Order> findByStatus(String status);
}
