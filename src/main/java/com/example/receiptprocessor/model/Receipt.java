package com.example.receiptprocessor.model;

import java.util.List;
import java.util.Objects;

public class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String total;

    // Constructors
    public Receipt() {
    }

    // Constructor
    public Receipt(String retailer, String purchaseDate, String purchaseTime, List<Item> items, String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = total;
    }

    // Getters
    public String getRetailer() {
        return retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getTotal() {
        return total;
    }

    // Setters
    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    // Methods to be used
    @Override
    public String toString() {
        return "Receipt{" +
                "retailer='" + retailer + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", items=" + items +
                ", total='" + total + '\'' +
                '}';
    }

    //To compare objects based on their field values and not their memory references
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(retailer, receipt.retailer) &&
                Objects.equals(purchaseDate, receipt.purchaseDate) &&
                Objects.equals(purchaseTime, receipt.purchaseTime) &&
                Objects.equals(items, receipt.items) &&
                Objects.equals(total, receipt.total);
    }

    // For easy storage in HashMaps/HashSet
    @Override
    public int hashCode() {
        return Objects.hash(retailer, purchaseDate, purchaseTime, items, total);
    }
}







/*
We can use Lombok so that we have the boilerplate code in m place. The following would be the implementation using Lombok

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String total;
}
*/

