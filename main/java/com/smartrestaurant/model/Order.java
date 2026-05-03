package com.smartrestaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Table number is required")
    @Min(value = 1, message = "Table number must be between 1 and 10")
    @Max(value = 10, message = "Table number must be between 1 and 10")
    @Column(name = "table_no", nullable = false)
    private Integer tableNo;

    @NotBlank(message = "Customer name is required")
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Dish name is required")
    @Column(name = "dish", nullable = false)
    private String dish;

    @Min(value = 1, message = "Price must be positive")
    @Column(name = "price", nullable = false)
    private int price;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "supervisor", nullable = false)
    private String supervisor;

    // Constructors
    public Order() {}

    public Order(Integer tableNo, String customerName, String phone,
                 String dish, int price, int quantity,
                 String status, String supervisor) {
        this.tableNo = tableNo;
        this.customerName = customerName;
        this.phone = phone;
        this.dish = dish;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.supervisor = supervisor;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getTableNo() { return tableNo; }
    public void setTableNo(Integer tableNo) { this.tableNo = tableNo; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDish() { return dish; }
    public void setDish(String dish) { this.dish = dish; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }
}
