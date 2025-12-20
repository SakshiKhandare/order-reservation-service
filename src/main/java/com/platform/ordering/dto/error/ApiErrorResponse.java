package com.platform.ordering.dto.error;

import java.time.Instant;

public class ApiErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String message;

    public ApiErrorResponse(int status, String message) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
