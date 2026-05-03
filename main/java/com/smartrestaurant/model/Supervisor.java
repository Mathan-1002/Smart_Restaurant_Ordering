package com.smartrestaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "supervisors")
public class Supervisor {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "table_start", nullable = false)
    private int tableStart;

    @Column(name = "table_end", nullable = false)
    private int tableEnd;

    // Constructors
    public Supervisor() {}

    public Supervisor(String userId, String password, String name, int tableStart, int tableEnd) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.tableStart = tableStart;
        this.tableEnd = tableEnd;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getTableStart() { return tableStart; }
    public void setTableStart(int tableStart) { this.tableStart = tableStart; }

    public int getTableEnd() { return tableEnd; }
    public void setTableEnd(int tableEnd) { this.tableEnd = tableEnd; }
}
