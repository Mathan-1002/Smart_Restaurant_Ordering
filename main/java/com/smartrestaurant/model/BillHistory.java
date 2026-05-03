package com.smartrestaurant.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill_history")
public class BillHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_no", nullable = false)
    private int tableNo;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "supervisor", nullable = false)
    private String supervisor;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    @Column(name = "gst", nullable = false)
    private double gst;

    @Column(name = "grand_total", nullable = false)
    private double grandTotal;

    @Column(name = "billed_at", nullable = false)
    private LocalDateTime billedAt;

    @Column(name = "items_json", columnDefinition = "TEXT")
    private String itemsJson;

    public BillHistory() {}

    public BillHistory(int tableNo, String customerName, String phone,
                       String supervisor, double subtotal, double gst,
                       double grandTotal, LocalDateTime billedAt, String itemsJson) {
        this.tableNo      = tableNo;
        this.customerName = customerName;
        this.phone        = phone;
        this.supervisor   = supervisor;
        this.subtotal     = subtotal;
        this.gst          = gst;
        this.grandTotal   = grandTotal;
        this.billedAt     = billedAt;
        this.itemsJson    = itemsJson;
    }

    // Getters
    public Long getId()             { return id; }
    public int getTableNo()         { return tableNo; }
    public String getCustomerName() { return customerName; }
    public String getPhone()        { return phone; }
    public String getSupervisor()   { return supervisor; }
    public double getSubtotal()     { return subtotal; }
    public double getGst()          { return gst; }
    public double getGrandTotal()   { return grandTotal; }
    public LocalDateTime getBilledAt() { return billedAt; }
    public String getItemsJson()    { return itemsJson; }

    // Setters
    public void setTableNo(int tableNo)             { this.tableNo = tableNo; }
    public void setCustomerName(String customerName){ this.customerName = customerName; }
    public void setPhone(String phone)              { this.phone = phone; }
    public void setSupervisor(String supervisor)    { this.supervisor = supervisor; }
    public void setSubtotal(double subtotal)        { this.subtotal = subtotal; }
    public void setGst(double gst)                  { this.gst = gst; }
    public void setGrandTotal(double grandTotal)    { this.grandTotal = grandTotal; }
    public void setBilledAt(LocalDateTime billedAt) { this.billedAt = billedAt; }
    public void setItemsJson(String itemsJson)      { this.itemsJson = itemsJson; }
}
