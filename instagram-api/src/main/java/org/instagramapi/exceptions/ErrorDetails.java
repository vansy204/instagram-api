package org.instagramapi.exceptions;

import java.time.LocalDateTime;
import java.util.Locale;

public class ErrorDetails {
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetails(String message, String details, LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public ErrorDetails() {
    }
}
