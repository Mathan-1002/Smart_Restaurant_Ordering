package com.smartrestaurant.service;

import com.smartrestaurant.exception.InvalidRequestException;
import com.smartrestaurant.exception.ResourceNotFoundException;
import com.smartrestaurant.exception.UnauthorizedException;
import com.smartrestaurant.model.BillHistory;
import com.smartrestaurant.model.BillResponse;
import com.smartrestaurant.model.Order;
import com.smartrestaurant.model.Supervisor;
import com.smartrestaurant.repository.BillHistoryRepository;
import com.smartrestaurant.repository.OrderRepository;
import com.smartrestaurant.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BillHistoryRepository billHistoryRepository;

    // ─── Supervisor Login (same validation as original) ───────────────────────
    public Supervisor login(String userId, String password) {
        return supervisorRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(() -> new UnauthorizedException("Invalid Login. Please check your User ID and Password."));
    }

    // ─── Get table status for supervisor's range ──────────────────────────────
    public List<Map<String, Object>> getTableStatus(String userId, String password) {
        Supervisor sup = login(userId, password);

        List<Map<String, Object>> tables = new ArrayList<>();
        for (int t = sup.getTableStart(); t <= sup.getTableEnd(); t++) {
            Map<String, Object> tableInfo = new LinkedHashMap<>();
            tableInfo.put("tableNo", t);
            boolean occupied = orderRepository.existsByTableNo(t);
            tableInfo.put("status", occupied ? "Occupied" : "Free");
            tables.add(tableInfo);
        }
        return tables;
    }

    // ─── Get pending orders for a table (supervisor dashboard) ───────────────
    public List<Order> getPendingOrders(String userId, String password, int tableNo) {
        Supervisor sup = login(userId, password);
        validateTableForSupervisor(tableNo, sup);
        return orderRepository.findByTableNoAndStatus(tableNo, "Pending");
    }

    // ─── Mark an order as Supplied ────────────────────────────────────────────
    public Order markSupplied(String userId, String password, Long orderId) {
        Supervisor sup = login(userId, password);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        validateTableForSupervisor(order.getTableNo(), sup);

        if (!order.getStatus().equals("Pending"))
            throw new InvalidRequestException("Order is already " + order.getStatus());

        order.setStatus("Supplied");
        return orderRepository.save(order);
    }

    // ─── Generate Bill (same logic as original Bill.java + 5% GST) ───────────
    public BillResponse generateBill(String userId, String password, int tableNo) {
        Supervisor sup = login(userId, password);
        validateTableForSupervisor(tableNo, sup);

        List<Order> allOrders = orderRepository.findByTableNo(tableNo);
        if (allOrders.isEmpty())
            throw new ResourceNotFoundException("No orders found for table " + tableNo);

        // Check all dishes are supplied (same as original logic)
        boolean allSupplied = allOrders.stream()
                .noneMatch(o -> o.getStatus().equals("Pending"));

        if (!allSupplied)
            throw new InvalidRequestException("All dishes are not supplied yet. Please mark all orders as Supplied before generating bill.");

        // Build bill
        BillResponse bill = new BillResponse();
        bill.setTableNo(tableNo);
        bill.setSupervisor(allOrders.get(0).getSupervisor());
        bill.setCustomerName(allOrders.get(0).getCustomerName());
        bill.setPhone(allOrders.get(0).getPhone());

        List<BillResponse.BillItem> items = new ArrayList<>();
        double subtotal = 0;

        for (Order o : allOrders) {
            double amount = (double) o.getPrice() * o.getQuantity();
            items.add(new BillResponse.BillItem(
                    o.getDish(), o.getQuantity(), o.getPrice(), amount));
            subtotal += amount;
        }

        double gst = subtotal * 0.05;        // 5% GST as in original
        double grandTotal = subtotal + gst;

        bill.setItems(items);
        bill.setSubtotal(subtotal);
        bill.setGst(gst);
        bill.setGrandTotal(grandTotal);

        // ── Persist bill history before clearing table ────────────────────────
        String itemsJson = buildItemsJson(items);
        BillHistory history = new BillHistory(
                tableNo,
                bill.getCustomerName(),
                bill.getPhone(),
                bill.getSupervisor(),
                subtotal,
                gst,
                grandTotal,
                LocalDateTime.now(),
                itemsJson
        );
        billHistoryRepository.save(history);

        // Clear table orders after bill generation (same as original clearTableOrders)
        orderRepository.deleteAll(allOrders);

        return bill;
    }

    // ─── Validate that table belongs to supervisor ─────────────────────────────
    private void validateTableForSupervisor(int tableNo, Supervisor sup) {
        if (tableNo < sup.getTableStart() || tableNo > sup.getTableEnd())
            throw new InvalidRequestException(
                    "Table " + tableNo + " is not under your supervision. " +
                    "You manage tables " + sup.getTableStart() + "-" + sup.getTableEnd());
    }

    // ─── Build a simple JSON string from bill items (no Jackson dependency) ───
    private String buildItemsJson(List<BillResponse.BillItem> items) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            BillResponse.BillItem item = items.get(i);
            sb.append("{")
              .append("\"dish\":\"").append(item.getDish().replace("\"", "\\\"")).append("\",")
              .append("\"quantity\":").append(item.getQuantity()).append(",")
              .append("\"price\":").append(item.getPrice()).append(",")
              .append("\"amount\":").append(item.getAmount())
              .append("}");
            if (i < items.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    // ─── Get bill history for a table ─────────────────────────────────────────
    public List<BillHistory> getBillHistoryByTable(String userId, String password, int tableNo) {
        Supervisor sup = login(userId, password);
        validateTableForSupervisor(tableNo, sup);
        return billHistoryRepository.findByTableNoOrderByBilledAtDesc(tableNo);
    }

    // ─── Get all bill history for supervisor's tables ─────────────────────────
    public List<BillHistory> getBillHistoryBySupervisor(String userId, String password) {
        Supervisor sup = login(userId, password);
        return billHistoryRepository.findBySupervisorOrderByBilledAtDesc(sup.getName());
    }
}
