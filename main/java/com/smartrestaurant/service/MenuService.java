package com.smartrestaurant.service;

import com.smartrestaurant.model.MenuItem;
import com.smartrestaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // ─── Same time-slot logic as original Menu.java ───────────────────────────
    public int getTimeSlot() {
        LocalTime time = LocalTime.now();
        double current = time.getHour() + (time.getMinute() / 60.0);

        if (current >= 6.0  && current < 11.5) return 1; // Breakfast
        if (current >= 11.5 && current < 15.5) return 2; // Lunch
        if (current >= 15.5 && current < 18.5) return 3; // Snacks
        if (current >= 18.5 && current < 23.0) return 4; // Dinner
        return 0; // Closed
    }

    public String getSlotName(int slot) {
        return switch (slot) {
            case 1 -> "Breakfast (6:00 AM - 11:30 AM)";
            case 2 -> "Lunch (11:30 AM - 3:30 PM)";
            case 3 -> "Snacks (3:30 PM - 6:30 PM)";
            case 4 -> "Dinner (6:30 PM - 11:00 PM)";
            default -> "Closed";
        };
    }

    // ─── Get distinct varieties for current time slot ─────────────────────────
    public Map<String, Object> getMenuForCurrentSlot() {
        int slot = getTimeSlot();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("slot", slot);
        response.put("slotName", getSlotName(slot));

        if (slot == 0) {
            response.put("open", false);
            response.put("message", "Hotel is CLOSED now (Opens at 6:00 AM)");
            return response;
        }

        response.put("open", true);
        List<MenuItem> items = menuItemRepository.findBySlotOrderByVariety(slot);

        // Group by variety
        Map<String, List<Map<String, Object>>> varietyMap = new LinkedHashMap<>();
        for (MenuItem m : items) {
            String imageName = m.getName().replace(" ", "_") + ".png";
            String imageUrl  = "/images/" + imageName;
            Map<String, Object> dish = new LinkedHashMap<>();
            dish.put("id",       m.getId());
            dish.put("name",     m.getName());
            dish.put("qty",      m.getQty());
            dish.put("price",    m.getPrice());
            dish.put("imageUrl", imageUrl);
            varietyMap.computeIfAbsent(m.getVarietyName(), k -> new ArrayList<>())
                      .add(dish);
        }
        response.put("varieties", varietyMap);
        return response;
    }

    // ─── Get dishes for a specific slot + variety ─────────────────────────────
    public List<MenuItem> getDishesBySlotAndVariety(int slot, int variety) {
        return menuItemRepository.findBySlotAndVariety(slot, variety);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new com.smartrestaurant.exception.ResourceNotFoundException(
                        "Menu item not found with id: " + id));
    }
}
