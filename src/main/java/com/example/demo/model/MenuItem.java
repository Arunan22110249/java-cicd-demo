package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String id;
    private String name;
    private List<SaleRecord> history = new ArrayList<>();

    public MenuItem() {}

    public MenuItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<SaleRecord> getHistory() { return history; }
    public void setHistory(List<SaleRecord> history) { this.history = history; }
}
