package com.smartrestaurant.controller;

import com.smartrestaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * GET /api/menu/current
     * Returns the menu for the current time slot (same logic as original Menu.getTimeSlot + showVariety + showDishes)
     */
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentMenu() {
        return ResponseEntity.ok(menuService.getMenuForCurrentSlot());
    }

    /**
     * GET /api/menu/slot
     * Returns current time slot info
     */
    @GetMapping("/slot")
    public ResponseEntity<Map<String, Object>> getTimeSlot() {
        int slot = menuService.getTimeSlot();
        return ResponseEntity.ok(Map.of(
                "slot", slot,
                "slotName", menuService.getSlotName(slot),
                "open", slot != 0
        ));
    }
}
