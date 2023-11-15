package com.example.receiptprocessor.service;

import com.example.receiptprocessor.dto.ProcessRequest;
import com.example.receiptprocessor.exception.ReceiptNotFoundException;
import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.service.ReceiptProcessorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;

public class ReceiptProcessorServiceTest {

    @Test
    void testProcessReceiptSuccess() {
        ReceiptProcessorService service = new ReceiptProcessorService();
        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "150.00");

        String receiptId = service.processReceipt(request);

        // Check if receiptId is not null (indicating success)
        assertEquals(98, service.getPoints(receiptId));
    }

    @Test
    void testGetPointsSuccess() {
        ReceiptProcessorService service = new ReceiptProcessorService();
        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "150.00");

        String receiptId = service.processReceipt(request);

        int points = service.getPoints(receiptId);

        // Check if points are as expected
        assertEquals(98, points);
    }

    @Test
    void testGetPointsReceiptNotFoundException() {
        ReceiptProcessorService service = new ReceiptProcessorService();

        // Use a non-existent receiptId
        assertThrows(ReceiptNotFoundException.class, () -> service.getPoints("nonExistentReceiptId"));
    }

    @Test
    void testCalculatePointsRule1() {
        ReceiptProcessorService service = new ReceiptProcessorService();
        ProcessRequest request = new ProcessRequest("Alpha123", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "150.00");

        int points = service.calculatePoints(request);

        // Check if points are calculated based on Rule 1
        assertEquals(93, points); // "Alpha123" has 8 alphanumeric characters
    }

    @Test
    void testCalculatePointsRule2() {
        ReceiptProcessorService service = new ReceiptProcessorService();
        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "100.00");

        int points = service.calculatePoints(request);

        // Check if points are calculated based on Rule 2
        assertEquals(98, points); // Total is a round dollar amount
    }

    @Test
    void testCalculatePointsRule3() {
        ReceiptProcessorService service = new ReceiptProcessorService();
        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "5.25");

        int points = service.calculatePoints(request);

        // Check if points are calculated based on Rule 3
        assertEquals(48, points); // Total is a multiple of 0.25
    }

    @Test
    void testCalculatePointsRule4() {
        ReceiptProcessorService service = new ReceiptProcessorService();
        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Arrays.asList(new Item("Item1", "10.00"), new Item("Item2", "5.00")), "150.00");

        int points = service.calculatePoints(request);

        // Check if points are calculated based on Rule 4
        assertEquals(103, points); // 2 items, so 5 points
    }

}
