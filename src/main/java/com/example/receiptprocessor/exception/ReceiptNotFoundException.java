package com.example.receiptprocessor.exception;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String receiptId) {
        super("Receipt not found for ID: " + receiptId);
    }
}
