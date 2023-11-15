package com.example.receiptprocessor.validator;

import com.example.receiptprocessor.dto.ProcessRequest;
import com.example.receiptprocessor.exception.ReceiptValidationException;
import com.example.receiptprocessor.model.Item;

import java.util.List;
import java.util.regex.Pattern;

public class ReceiptValidator {

    public static void validateProcessRequest(ProcessRequest processRequest) {
        validateRetailer(processRequest.getRetailer());
        validatePurchaseDate(processRequest.getPurchaseDate());
        validatePurchaseTime(processRequest.getPurchaseTime());
        validateItems(processRequest.getItems());
        validateTotal(processRequest.getTotal());
    }

    public static void validateRetailer(String retailer) {
        if (retailer == null || retailer.trim().isEmpty()) {
            throw new ReceiptValidationException("Retailer cannot be null or empty");
        }
    }

    public static void validatePurchaseDate(String purchaseDate) {
        if (purchaseDate == null || purchaseDate.trim().isEmpty()) {
            throw new ReceiptValidationException("Purchase date cannot be null or empty");
        }
        // Assuming a simple date format (YYYY-MM-DD)
        if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", purchaseDate)) {
            throw new ReceiptValidationException("Invalid purchase date format");
        }
    }

    public static void validatePurchaseTime(String purchaseTime) {
        if (purchaseTime == null || purchaseTime.trim().isEmpty()) {
            throw new ReceiptValidationException("Purchase time cannot be null or empty");
        }
        // Assuming a simple time format (HH:mm)
        if (!Pattern.matches("\\d{2}:\\d{2}", purchaseTime)) {
            throw new ReceiptValidationException("Invalid purchase time format");
        }
    }

    public static void validateItems(List<Item> items) {
        if (items == null || items.isEmpty()) {
            throw new ReceiptValidationException("Items cannot be null or empty");
        }

        for (Item item : items) {
            validateItem(item);
        }
    }

    public static void validateItem(Item item) {
        if (item == null) {
            throw new ReceiptValidationException("Item cannot be null");
        }

        validateShortDescription(item.getShortDescription());
        validatePrice(item.getPrice());
    }

    public static void validateShortDescription(String shortDescription) {
        if (shortDescription == null || shortDescription.trim().isEmpty()) {
            throw new ReceiptValidationException("Short description cannot be null or empty");
        }
    }

    public static void validatePrice(String price) {
        if (price == null || price.trim().isEmpty()) {
            throw new ReceiptValidationException("Price cannot be null or empty");
        }
        try {
            double parsedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new ReceiptValidationException("Invalid price format");
        }
    }

    public static void validateTotal(String total) {
        if (total == null || total.trim().isEmpty()) {
            throw new ReceiptValidationException("Total cannot be null or empty");
        }

        try {
            double parsedTotal = Double.parseDouble(total);
        } catch (NumberFormatException e) {
            throw new ReceiptValidationException("Invalid total format");
        }
    }
}
