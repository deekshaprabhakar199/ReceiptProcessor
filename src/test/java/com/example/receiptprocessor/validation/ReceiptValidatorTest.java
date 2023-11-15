package com.example.receiptprocessor.validation;

import com.example.receiptprocessor.exception.ReceiptValidationException;
import com.example.receiptprocessor.dto.ProcessRequest;
import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.validator.ReceiptValidator;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReceiptValidatorTest {

    @Test
    public void testValidateRetailer() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateRetailer(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateRetailer(""));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateRetailer("   "));
    }

    @Test
    public void testValidatePurchaseDate() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePurchaseDate(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePurchaseDate(""));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePurchaseDate("invalidDate"));
    }

    @Test
    public void testValidatePurchaseTime() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePurchaseTime(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePurchaseTime(""));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePurchaseTime("invalidTime"));
    }

    @Test
    public void testValidateItems() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateItems(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateItems(Collections.emptyList()));
    }

    @Test
    public void testValidateItem() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateItem(null));
    }

    @Test
    public void testValidateShortDescription() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateShortDescription(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateShortDescription(""));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateShortDescription("   "));
    }

    @Test
    public void testValidatePrice() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePrice(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validatePrice("invalidPrice"));
    }

    @Test
    public void testValidateTotal() {
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateTotal(null));
        assertThrows(ReceiptValidationException.class, () -> ReceiptValidator.validateTotal("invalidTotal"));
    }
}

