package com.example.simplesavings;

public class Entry {
    public int id;
    public double amount;
    public String note;
    public String type;

    public Entry(int id, double amount, String note, String type) {
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.type = type;
    }
}