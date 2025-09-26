package com.example.simplesavings;

public class Entry {
    public double amount;
    public String note;
    public String type;

    public Entry(double amount, String note, String type) {
        this.amount = amount;
        this.note = note;
        this.type = type;
    }
}