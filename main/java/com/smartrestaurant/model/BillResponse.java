package com.smartrestaurant.model;

import java.util.List;

public class BillResponse {

    private int tableNo;
    private String customerName;
    private String phone;
    private String supervisor;
    private List<BillItem> items;
    private double subtotal;
    private double gst;
    private double grandTotal;

    public static class BillItem {
        private String dish;
        private int quantity;
        private double price;
        private double amount;

        public BillItem(String dish, int quantity, double price, double amount) {
            this.dish = dish;
            this.quantity = quantity;
            this.price = price;
            this.amount = amount;
        }

        public String getDish() { return dish; }
        public void setDish(String dish) { this.dish = dish; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }

    // Constructors
    public BillResponse() {}

    // Getters and Setters
    public int getTableNo() { return tableNo; }
    public void setTableNo(int tableNo) { this.tableNo = tableNo; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getGst() { return gst; }
    public void setGst(double gst) { this.gst = gst; }

    public double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(double grandTotal) { this.grandTotal = grandTotal; }
}
