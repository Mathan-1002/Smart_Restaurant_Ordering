package com.smartrestaurant.repository;

import com.smartrestaurant.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, String> {

    // Find by user ID and password (for login validation)
    Optional<Supervisor> findByUserIdAndPassword(String userId, String password);
}
