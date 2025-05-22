package com.insta.instagram_api.exceptions;

import java.time.LocalDateTime;

// 4번째 강의 59:36
public class ErrorDetails {
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetails() {

    }

    public ErrorDetails(String message, String details, LocalDateTime timestamp) {
        super();
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }
}
