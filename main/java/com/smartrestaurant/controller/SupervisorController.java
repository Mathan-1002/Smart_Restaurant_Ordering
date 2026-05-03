package com.smartrestaurant.controller;

import com.smartrestaurant.model.BillHistory;
import com.smartrestaurant.model.BillResponse;
import com.smartrestaurant.model.Order;
import com.smartrestaurant.model.Supervisor;
import com.smartrestaurant.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supervisor")
@CrossOrigin(origins = "*")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    /**
     * POST /api/supervisor/login
     * Validate credentials and return supervisor info
     * Body: { userId, password }
     */
    @PostMapping("/login")
    public ResponseEntity<Supervisor> login(@RequestBody Map<String, String> body) {
        String userId   = body.get("userId");
        String password = body.get("password");
        Supervisor sup  = supervisorService.login(userId, password);
        return ResponseEntity.ok(sup);
    }

    /**
     * POST /api/supervisor/tables
     * Get table occupancy for the logged-in supervisor
     * Body: { userId, password }
     */
    @PostMapping("/tables")
    public ResponseEntity<List<Map<String, Object>>> getTableStatus(
            @RequestBody Map<String, String> body) {

        List<Map<String, Object>> tables =
                supervisorService.getTableStatus(body.get("userId"), body.get("password"));
        return ResponseEntity.ok(tables);
    }

    /**
     * POST /api/supervisor/table/{tableNo}/orders
     * Get pending orders for a specific table
     * Body: { userId, password }
     */
    @PostMapping("/table/{tableNo}/orders")
    public ResponseEntity<List<Order>> getTableOrders(
            @PathVariable int tableNo,
            @RequestBody Map<String, String> body) {

        List<Order> orders =
                supervisorService.getPendingOrders(body.get("userId"), body.get("password"), tableNo);
        return ResponseEntity.ok(orders);
    }

    /**
     * PUT /api/supervisor/order/{orderId}/supply
     * Mark an order as Supplied
     * Body: { userId, password }
     */
    @PutMapping("/order/{orderId}/supply")
    public ResponseEntity<Order> markSupplied(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> body) {

        Order updated =
                supervisorService.markSupplied(body.get("userId"), body.get("password"), orderId);
        return ResponseEntity.ok(updated);
    }

    /**
     * POST /api/supervisor/table/{tableNo}/bill
     * Generate bill for a table (only if all orders Supplied)
     * Body: { userId, password }
     */
    @PostMapping("/table/{tableNo}/bill")
    public ResponseEntity<BillResponse> generateBill(
            @PathVariable int tableNo,
            @RequestBody Map<String, String> body) {

        BillResponse bill =
                supervisorService.generateBill(body.get("userId"), body.get("password"), tableNo);
        return ResponseEntity.ok(bill);
    }

    /**
     * POST /api/supervisor/table/{tableNo}/bill-history
     * Get all past bills for a specific table
     * Body: { userId, password }
     */
    @PostMapping("/table/{tableNo}/bill-history")
    public ResponseEntity<List<BillHistory>> getBillHistoryByTable(
            @PathVariable int tableNo,
            @RequestBody Map<String, String> body) {

        List<BillHistory> history =
                supervisorService.getBillHistoryByTable(body.get("userId"), body.get("password"), tableNo);
        return ResponseEntity.ok(history);
    }

    /**
     * POST /api/supervisor/bill-history
     * Get all past bills for this supervisor's tables
     * Body: { userId, password }
     */
    @PostMapping("/bill-history")
    public ResponseEntity<List<BillHistory>> getBillHistory(
            @RequestBody Map<String, String> body) {

        List<BillHistory> history =
                supervisorService.getBillHistoryBySupervisor(body.get("userId"), body.get("password"));
        return ResponseEntity.ok(history);
    }
}
