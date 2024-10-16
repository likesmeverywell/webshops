package com.likesmeverywell.Webshop.repository;

import com.likesmeverywell.Webshop.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
        // You can define custom query methods if needed
}

