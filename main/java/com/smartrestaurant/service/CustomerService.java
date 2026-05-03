package com.smartrestaurant.service;

import com.smartrestaurant.exception.InvalidRequestException;
import com.smartrestaurant.exception.ResourceNotFoundException;
import com.smartrestaurant.model.MenuItem;
import com.smartrestaurant.model.Order;
import com.smartrestaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuService menuService;

    // ─── Assign supervisor based on table number (same logic as original) ──────
    private String assignSupervisor(int tableNo) {
        if (tableNo >= 1 && tableNo <= 3)  return "Supervisor 1";
        if (tableNo >= 4 && tableNo <= 6)  return "Supervisor 2";
        return "Supervisor 3";
    }

    // ─── Validate table number ────────────────────────────────────────────────
    private void validateTable(int tableNo) {
        if (tableNo < 1 || tableNo > 10)
            throw new InvalidRequestException("Invalid Table Number. Must be between 1 and 10.");
    }

    // ─── Get customer info if table already has orders ─────────────────────────
    public Map<String, String> getTableCustomerInfo(int tableNo) {
        validateTable(tableNo);
        List<Order> existing = orderRepository.findByTableNo(tableNo);
        Map<String, String> info = new HashMap<>();
        if (!existing.isEmpty()) {
            info.put("customerName", existing.get(0).getCustomerName());
            info.put("phone", existing.get(0).getPhone());
            info.put("exists", "true");
        } else {
            info.put("exists", "false");
        }
        return info;
    }

    // ─── Place an order ────────────────────────────────────────────────────────
    public Order placeOrder(int tableNo, String customerName, String phone,
                            Long menuItemId, int quantity) {

        validateTable(tableNo);

        // Quantity validation (same as original)
        if (quantity <= 0) {
            quantity = 1;
        }

        // Check hotel is open
        int slot = menuService.getTimeSlot();
        if (slot == 0)
            throw new InvalidRequestException("Hotel is CLOSED now. Orders cannot be placed.");

        // Fetch menu item
        MenuItem item = menuService.getMenuItemById(menuItemId);

        // If table already has orders, use existing customer info
        List<Order> existing = orderRepository.findByTableNo(tableNo);
        if (!existing.isEmpty()) {
            customerName = existing.get(0).getCustomerName();
            phone = existing.get(0).getPhone();
        } else {
            if (customerName == null || customerName.isBlank())
                throw new InvalidRequestException("Customer name is required for new table.");
            if (phone == null || phone.isBlank())
                throw new InvalidRequestException("Phone number is required for new table.");
        }

        String supervisor = assignSupervisor(tableNo);

        Order order = new Order(
                tableNo,
                customerName,
                phone,
                item.getName(),
                item.getPrice(),
                quantity,
                "Pending",
                supervisor
        );

        return orderRepository.save(order);
    }

    // ─── Get all orders (pending) for a table ────────────────────────────────
    public List<Order> getTableOrders(int tableNo) {
        validateTable(tableNo);
        return orderRepository.findByTableNoAndStatus(tableNo, "Pending");
    }

    // ─── Get order summary for a table (all statuses) ────────────────────────
    public List<Order> getOrderSummary(int tableNo) {
        validateTable(tableNo);
        return orderRepository.findByTableNo(tableNo);
    }

    // ─── Cancel a pending order ───────────────────────────────────────────────
    public void cancelOrder(Long orderId, int tableNo) {
        validateTable(tableNo);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        if (order.getTableNo() != tableNo)
            throw new InvalidRequestException("Order does not belong to table " + tableNo);

        if (!order.getStatus().equals("Pending"))
            throw new InvalidRequestException("Only Pending orders can be cancelled. This order is: " + order.getStatus());

        orderRepository.delete(order);
    }
}
