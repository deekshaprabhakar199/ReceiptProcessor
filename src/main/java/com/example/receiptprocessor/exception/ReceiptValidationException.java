package com.example.receiptprocessor.exception;

public class ReceiptValidationException extends IllegalArgumentException {
    public ReceiptValidationException(String message) {
        super(message);
    }
}
