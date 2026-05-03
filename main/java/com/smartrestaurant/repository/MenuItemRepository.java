package com.smartrestaurant.repository;

import com.smartrestaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Find all items for a specific time slot
    List<MenuItem> findBySlot(int slot);

    // Find all items for a specific slot and variety
    List<MenuItem> findBySlotAndVariety(int slot, int variety);

    // Find distinct variety names for a slot
    List<MenuItem> findBySlotOrderByVariety(int slot);
}
