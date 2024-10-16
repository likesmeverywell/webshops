package com.likesmeverywell.Webshop.repository;

import com.likesmeverywell.Webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
