package com.example.jstore_android_feno;

import java.util.ArrayList;

public class Invoice
{
    private int id;
    private String date;
    private boolean isActive;
    private ArrayList<String> items;
    private int totalPrice;
    private String invoiceType;
    private String invoiceStatus;
    private int installmentPeriod;
    private int installmentPrice;

    public Invoice(int id, String date, ArrayList<String> items, int totalPrice, String invoiceType, String invoiceStatus) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalPrice = totalPrice;
        this.invoiceType = invoiceType;
        this.invoiceStatus = invoiceStatus;
    }

    public Invoice(int id, String date, ArrayList<String> items, int totalPrice, String invoiceType, String invoiceStatus, int installmentPeriod, int installmentPrice) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalPrice = totalPrice;
        this.invoiceType = invoiceType;
        this.invoiceStatus = invoiceStatus;
        this.installmentPeriod = installmentPeriod;
        this.installmentPrice = installmentPrice;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public boolean isActive() {
        return isActive;
    }

    public ArrayList<String> getItem() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public int getInstallmentPeriod() {
        return installmentPeriod;
    }

    public int getInstallmentPrice() {
        return installmentPrice;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
