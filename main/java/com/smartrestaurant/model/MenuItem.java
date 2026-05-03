package com.smartrestaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "slot", nullable = false)
    private int slot;

    @Column(name = "variety", nullable = false)
    private int variety;

    @Column(name = "variety_name", nullable = false)
    private String varietyName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "price", nullable = false)
    private int price;

    // Constructors
    public MenuItem() {}

    public MenuItem(int slot, int variety, String varietyName,
                    String name, int qty, int price) {
        this.slot = slot;
        this.variety = variety;
        this.varietyName = varietyName;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getSlot() { return slot; }
    public void setSlot(int slot) { this.slot = slot; }

    public int getVariety() { return variety; }
    public void setVariety(int variety) { this.variety = variety; }

    public String getVarietyName() { return varietyName; }
    public void setVarietyName(String varietyName) { this.varietyName = varietyName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
