package com.likesmeverywell.Webshop.repository;

import com.likesmeverywell.Webshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
