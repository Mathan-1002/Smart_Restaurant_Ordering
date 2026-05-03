package com.smartrestaurant.controller;

import com.smartrestaurant.model.Order;
import com.smartrestaurant.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * GET /api/customer/table/{tableNo}/info
     * Check if table already has a customer (reuses existing name/phone)
     */
    @GetMapping("/table/{tableNo}/info")
    public ResponseEntity<Map<String, String>> getTableInfo(@PathVariable int tableNo) {
        return ResponseEntity.ok(customerService.getTableCustomerInfo(tableNo));
    }

    /**
     * GET /api/customer/table/{tableNo}/orders
     * Get all pending orders for the table (used in cancel order screen)
     */
    @GetMapping("/table/{tableNo}/orders")
    public ResponseEntity<List<Order>> getTableOrders(@PathVariable int tableNo) {
        return ResponseEntity.ok(customerService.getTableOrders(tableNo));
    }

    /**
     * GET /api/customer/table/{tableNo}/summary
     * Get order summary (all statuses) for display
     */
    @GetMapping("/table/{tableNo}/summary")
    public ResponseEntity<List<Order>> getOrderSummary(@PathVariable int tableNo) {
        return ResponseEntity.ok(customerService.getOrderSummary(tableNo));
    }

    /**
     * POST /api/customer/order
     * Place a new order
     * Body: { tableNo, customerName, phone, menuItemId, quantity }
     */
    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> body) {
        int tableNo       = (Integer) body.get("tableNo");
        String name       = (String) body.getOrDefault("customerName", "");
        String phone      = (String) body.getOrDefault("phone", "");
        Long menuItemId   = Long.valueOf(body.get("menuItemId").toString());
        int quantity      = (Integer) body.get("quantity");

        Order saved = customerService.placeOrder(tableNo, name, phone, menuItemId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * DELETE /api/customer/order/{orderId}?tableNo=X
     * Cancel a pending order (same rule: only Pending orders can be cancelled)
     */
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Map<String, String>> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam int tableNo) {

        customerService.cancelOrder(orderId, tableNo);
        return ResponseEntity.ok(Map.of("message", "Order Cancelled Successfully"));
    }
}
