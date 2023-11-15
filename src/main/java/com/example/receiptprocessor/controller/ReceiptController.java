package com.example.receiptprocessor.controller;


import com.example.receiptprocessor.dto.ProcessRequest;
import com.example.receiptprocessor.dto.ProcessResponse;
import com.example.receiptprocessor.dto.PointsResponse;
import com.example.receiptprocessor.exception.ReceiptNotFoundException;
import com.example.receiptprocessor.exception.ReceiptValidationException;
import com.example.receiptprocessor.service.ReceiptProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController{

    private final ReceiptProcessorService receiptProcessorService;

    @Autowired
    public ReceiptController(ReceiptProcessorService receiptProcessorService) {
        this.receiptProcessorService = receiptProcessorService;
    }

    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(@RequestBody ProcessRequest processRequest) {
        try{
            // Call Service to process receipt
            String receiptId = receiptProcessorService.processReceipt(processRequest);

            // Return response
            ProcessResponse response = new ProcessResponse(receiptId);
            return ResponseEntity.ok(response);
        }
        catch (ReceiptValidationException e) {
            // Handle validation exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ReceiptNotFoundException e) {
            // Handle not found exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        // Call Service to get points for receipt
        int points = receiptProcessorService.getPoints(id);

        // Return response
        PointsResponse response = new PointsResponse(points);
        return ResponseEntity.ok(response);
    }
}
