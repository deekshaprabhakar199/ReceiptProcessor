package com.example.receiptprocessor.service;
import com.example.receiptprocessor.dto.ProcessRequest;
import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.exception.ReceiptNotFoundException;
import com.example.receiptprocessor.validator.ReceiptValidator;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReceiptProcessorService {

    // Simulated in-memory database
    private final Map<String, Integer> receiptPointsMap = new HashMap<>();

    public String processReceipt(ProcessRequest processRequest) {
        // Validate the processRequest
        ReceiptValidator.validateProcessRequest(processRequest);

        // Check if points are already calculated for this receipt
        String receiptId = findReceiptIdInDatabase(processRequest);

        if (receiptId == null) {
            // Points not found in the database, calculate points
            int points = calculatePoints(processRequest);
            // Store points in the database
            receiptId = storePointsInDatabase(processRequest, points);
        }

        return receiptId;
    }

    public int getPoints(String receiptId) {
        Integer points = receiptPointsMap.get(receiptId);
        // System.out.println("Received receiptId: " + receiptId);

        if (points == null) {
            throw new ReceiptNotFoundException("Receipt not found");
        }

        return points;
    }

    // Public method to find receipt ID in the database
    public String findReceiptIdInDatabase(ProcessRequest processRequest) {
        // Logic to check if receipt is already in the database
        // Return receipt ID if found, otherwise return null
        // In this example, we use a simple check based on retailer and total
        for (Map.Entry<String, Integer> entry : receiptPointsMap.entrySet()) {
            if (isSameReceipt(entry.getKey(), processRequest)) {
                return entry.getKey();
            }
        }
        return null;
    }

    // Public method to store points in the database
    public String storePointsInDatabase(ProcessRequest processRequest, int points) {
        String receiptId = UUID.randomUUID().toString();
        // Store the receipt ID and points in the database
        receiptPointsMap.put(receiptId, points);
        return receiptId;
    }

    // Public method to calculate points
    public int calculatePoints(ProcessRequest processRequest) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += processRequest.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        if (isRoundDollarAmount(processRequest.getTotal())) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (isMultipleOf25Cents(processRequest.getTotal())) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        if (processRequest.getItems() != null) {
            points += (processRequest.getItems().size() / 2) * 5;
        }

        // Rule 5: If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        if (processRequest.getItems() != null) {
            for (Item item : processRequest.getItems()) {
                if (item != null && item.getShortDescription() != null) {
                    int descriptionLength = item.getShortDescription().trim().length();
                    if (descriptionLength % 3 == 0) {
                        points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
                    }
                }
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd
        if (isDayOdd(processRequest.getPurchaseDate())) {
            points += 6;
        }

        // Rule 7: 10 points if the time of purchase is after 2:00pm and before 4:00pm
        if (isTimeInRange(processRequest.getPurchaseTime(), "14:00", "16:00")) {
            points += 10;
        }

        return points;
    }

    // Private method to check if a total is a round dollar amount with no cents
    private boolean isRoundDollarAmount(String total) {
        double amount = Double.parseDouble(total);
        // Check if the decimal part is zero
        return amount == Math.floor(amount);
    }

    // Private method to check if a total is a multiple of 0.25
    private boolean isMultipleOf25Cents(String total) {
        double amount = Double.parseDouble(total);
        // Check if the amount is a multiple of 0.25
        return (amount * 100) % 25 == 0;
    }


    // Private method to check if the day is odd
    private boolean isDayOdd(String date) {
        // Extract day from the date and check if it's odd
        int day = Integer.parseInt(date.split("-")[2]);
        return day % 2 != 0;
    }

    // Private method to check if the time is in a specific range
    private boolean isTimeInRange(String time, String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date currentTime = sdf.parse(time);
            Date rangeStartTime = sdf.parse(startTime);
            Date rangeEndTime = sdf.parse(endTime);

            return !currentTime.before(rangeStartTime) && currentTime.before(rangeEndTime);
        } catch (ParseException e) {
            // Handle the ParseException as needed
            e.printStackTrace();
            return false;
        }
    }


    // Private method to check if two receipts are the same
    private boolean isSameReceipt(String receiptId, ProcessRequest newProcessRequest) {
        // In this example, we check if retailer, total, and other relevant fields are the same
        int points = calculatePoints(newProcessRequest);

        return receiptPointsMap.containsKey(receiptId) &&
                receiptPointsMap.get(receiptId).equals(points);
    }

}
