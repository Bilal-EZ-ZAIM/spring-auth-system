package com.auth.demo.apiResponces;

import java.time.LocalDateTime;
import java.util.List;

import com.auth.demo.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonPropertyOrder({ "statusCode", "status", "message", "data", "errors", "timestamp" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final int statusCode;
    private final ResponseStatus status;
    private final String message;
    private final T data;
    private final List<?> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    // ✅ SUCCESS
    public ApiResponse(T data, String message, int statusCode) {
        this.status = ResponseStatus.SUCCESS;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.errors = null;
        this.timestamp = LocalDateTime.now();
    }

    // ✅ ERROR
    public ApiResponse(String message, List<?> errors, int statusCode) {
        this.status = ResponseStatus.ERROR;
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}