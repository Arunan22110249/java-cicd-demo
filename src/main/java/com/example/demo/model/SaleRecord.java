package com.example.demo.model;

import java.time.LocalDate;

public class SaleRecord {
    private LocalDate date;
    private int quantity;

    public SaleRecord() {}

    public SaleRecord(LocalDate date, int quantity) {
        this.date = date;
        this.quantity = quantity;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
