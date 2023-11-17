package com.example.receiptprocessor.service;

import com.example.receiptprocessor.controller.ReceiptController;
import com.example.receiptprocessor.dto.ProcessRequest;
import com.example.receiptprocessor.dto.ProcessResponse;
import com.example.receiptprocessor.exception.ReceiptNotFoundException;
import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.service.ReceiptProcessorService;
import com.example.receiptprocessor.validator.ReceiptValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



class ReceiptProcessorServiceTest {

    ReceiptProcessorService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        service = new ReceiptProcessorService();
    }

    @Test
    void calculatePointsForAlphanumeric() {
        // Arrange
        String retailer = "Test123";

        // Act
        int points = service.calculatePointsForAlphanumeric(retailer);

        // Assert
        assertEquals(7, points);
    }

    @Test
    void calculatePointsForRoundDollarAmount() {
        // Arrange
        String total = "10.00";

        // Act
        int points = service.calculatePointsForRoundDollarAmount(total);

        // Assert
        assertEquals(50, points);
    }

    @Test
    void calculatePointsForMultipleOf25Cents() {
        // Arrange
        String total = "7.50";

        // Act
        int points = service.calculatePointsForMultipleOf25Cents(total);

        // Assert
        assertEquals(25, points);
    }

    @Test
    void calculatePointsForItems() {
        // Arrange
        Item item1 = new Item("Item1", "5.00");
        Item item2 = new Item("Item2", "3.00");
        var items = Arrays.asList(item1, item2);

        // Act
        int points = service.calculatePointsForItems(items);

        // Assert
        assertEquals(5, points);
    }

    @Test
    void calculatePointsForDescriptionLength() {
        // Arrange
        Item item = new Item("TestItem", "10.00");
        var items = Collections.singletonList(item);

        // Act
        int points = service.calculatePointsForDescriptionLength(items);

        // Assert
        assertEquals(0, points);
    }

    @Test
    void calculatePointsForOddDay() {
        // Arrange
        String date = "2023-01-01";

        // Act
        int points = service.calculatePointsForOddDay(date);

        // Assert
        assertEquals(6, points);
    }

    @Test
    void calculatePointsForTimeRange() {
        // Arrange
        String time = "14:30";

        // Act
        int points = service.calculatePointsForTimeRange(time, "14:00", "16:00");

        // Assert
        assertEquals(10, points);
    }

    @Test
    void calculatePoints() {
        // Arrange
        ProcessRequest request = new ProcessRequest("Test123", "2023-01-01",
                "14:30", Collections.singletonList(new Item("Test", "5.00")), "5.00");

        // Act
        int expectedPoints = 7 + 50 + 25 + 6 + 10;
        int points = service.calculatePoints(request);

        // Assert
        assertEquals(expectedPoints, points);
    }


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
}






//class ReceiptProcessorServiceTest {
//
//    @Mock
//    private ReceiptProcessorService receiptProcessorService;
//
//    @InjectMocks
//    private ReceiptController receiptProcessorController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(receiptProcessorController).build();
//    }
//
//    // ReceiptProcessorService Tests
//    @Test
//    void calculatePointsForAlphanumeric() {
//        // Arrange
//        String retailer = "TestRetailer";
//        //when(receiptProcessorService.calculatePointsForAlphanumeric(retailer)).thenReturn(11);
//
//        // Act
//        int points = receiptProcessorService.calculatePointsForAlphanumeric(retailer);
//        System.out.println(points);
//
//        // Assert
//        assertEquals(11, points); // Assuming "TestRetailer" has 11 alphanumeric characters
//    }
//
//    @Test
//    void calculatePointsForRoundDollarAmount() {
//        // Arrange
//        String total = "10.00";
//        when(receiptProcessorService.calculatePointsForRoundDollarAmount(total))
//                .thenReturn(50);
//
//        // Act
//        int points = receiptProcessorService.calculatePointsForRoundDollarAmount(total);
//
//        // Assert
//        assertEquals(50, points);
//    }
//
//    @Test
//    void calculatePointsForMultipleOf25Cents() {
//        // Arrange
//        String total = "7.50";
//        when(receiptProcessorService.calculatePointsForMultipleOf25Cents(total))
//                .thenReturn(25);
//
//        // Act
//        int points = receiptProcessorService.calculatePointsForMultipleOf25Cents(total);
//
//        // Assert
//        assertEquals(25, points);
//    }
//
//    @Test
//    void calculatePointsForItems() {
//        // Arrange
//        List<Item> items = Arrays.asList(new Item("Item1", "5.00"), new Item("Item2", "3.00"));
//        when(receiptProcessorService.calculatePointsForItems(items))
//                .thenReturn(5);
//
//        // Act
//        int points = receiptProcessorService.calculatePointsForItems(items);
//
//
//        // Assert
//        assertEquals(5, points);
//    }
//
//    @Test
//    void calculatePointsForDescriptionLength() {
//        // Arrange
//        List<Item> items = Collections.singletonList(new Item("TestItem", "10.00"));
//
//        // Act
//        int points = receiptProcessorService.calculatePointsForDescriptionLength(items);
//
//        // Assert
//        assertEquals(2, points); // Assuming "TestItem" has 8 characters, which is a multiple of 3
//    }
//
//    @Test
//    void calculatePointsForOddDay() {
//        // Arrange
//        String date = "2022-01-01";
//
//        // Act
//        int points = receiptProcessorService.calculatePointsForOddDay(date);
//
//        // Assert
//        assertEquals(6, points);
//    }
//
//    @Test
//    void calculatePointsForTimeRange() {
//        // Arrange
//        String timeBeforeRange = "13:59";
//        String timeInRange = "14:30";
//        String timeAfterRange = "16:01";
//
//        // Act & Assert
//        assertEquals(0, receiptProcessorService.calculatePointsForTimeRange(timeBeforeRange, "14:00", "16:00"));
//        assertEquals(10, receiptProcessorService.calculatePointsForTimeRange(timeInRange, "14:00", "16:00"));
//        assertEquals(0, receiptProcessorService.calculatePointsForTimeRange(timeAfterRange, "14:00", "16:00"));
//    }
//
//    // ReceiptProcessorController Tests
//
//    @Test
//    void processReceipt() throws Exception {
//        // Arrange
//        ProcessRequest processRequest = new ProcessRequest();
//        when(receiptProcessorService.processReceipt(processRequest)).thenReturn("receipt-id");
//
//        // Act & Assert
//        mockMvc.perform(post("/receipts/process")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("receipt-id"));
//
//        // Verify that the service method was called
//        verify(receiptProcessorService, times(1)).processReceipt(processRequest);
//    }
//
//    @Test
//    void getPoints() throws Exception {
//        // Arrange
//        String receiptId = "receipt-id";
//        when(receiptProcessorService.getPoints(receiptId)).thenReturn(42);
//
//        // Act & Assert
//        mockMvc.perform(get("/receipts/{id}/points", receiptId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.points").value(42));
//
//        // Verify that the service method was called
//        verify(receiptProcessorService, times(1)).getPoints(receiptId);
//    }
//
//}
//
//



//package com.example.receiptprocessor.service;
//
//import com.example.receiptprocessor.dto.ProcessRequest;
//import com.example.receiptprocessor.exception.ReceiptNotFoundException;
//import com.example.receiptprocessor.model.Item;
//import com.example.receiptprocessor.service.ReceiptProcessorService;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//public class ReceiptProcessorServiceTest {
//
//    @Test
//    void testProcessReceiptSuccess() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "150.00");
//
//        String receiptId = service.processReceipt(request);
//
//        // Check if receiptId is not null (indicating success)
//        assertEquals(98, service.getPoints(receiptId));
//    }
//
//    @Test
//    void testGetPointsSuccess() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "150.00");
//
//        String receiptId = service.processReceipt(request);
//
//        int points = service.getPoints(receiptId);
//
//        // Check if points are as expected
//        assertEquals(98, points);
//    }
//
//    @Test
//    void testGetPointsReceiptNotFoundException() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//
//        // Use a non-existent receiptId
//        assertThrows(ReceiptNotFoundException.class, () -> service.getPoints("nonExistentReceiptId"));
//    }
//
//    @Test
//    void testCalculatePointsRule1() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//        ProcessRequest request = new ProcessRequest("Alpha123", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "150.00");
//
//        int points = service.calculatePoints(request);
//
//        // Check if points are calculated based on Rule 1
//        assertEquals(93, points); // "Alpha123" has 8 alphanumeric characters
//    }
//
//    @Test
//    void testCalculatePointsRule2() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "100.00");
//
//        int points = service.calculatePoints(request);
//
//        // Check if points are calculated based on Rule 2
//        assertEquals(98, points); // Total is a round dollar amount
//    }
//
//    @Test
//    void testCalculatePointsRule3() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Collections.singletonList(new Item("Item1", "10.00")), "5.25");
//
//        int points = service.calculatePoints(request);
//
//        // Check if points are calculated based on Rule 3
//        assertEquals(48, points); // Total is a multiple of 0.25
//    }
//
//    @Test
//    void testCalculatePointsRule4() {
//        ReceiptProcessorService service = new ReceiptProcessorService();
//        ProcessRequest request = new ProcessRequest("ValidRetailer", "2023-11-08", "14:30", Arrays.asList(new Item("Item1", "10.00"), new Item("Item2", "5.00")), "150.00");
//
//        int points = service.calculatePoints(request);
//
//        // Check if points are calculated based on Rule 4
//        assertEquals(103, points); // 2 items, so 5 points
//    }
//
//}
