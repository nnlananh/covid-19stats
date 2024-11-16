package com.example.covid19stats;

public class ResponseModel {
    private String status;
    private String message;

    // Constructor không tham số
    public ResponseModel() {
    }

    // Getter và Setter cho status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter và Setter cho message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
