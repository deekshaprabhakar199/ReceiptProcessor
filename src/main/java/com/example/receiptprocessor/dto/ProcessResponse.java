package com.example.receiptprocessor.dto;

public class ProcessResponse {
    private String id;

    // Constructors, getters, setters

    public ProcessResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}