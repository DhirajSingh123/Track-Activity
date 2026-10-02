package com.track.activity.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

    private int statusCode;
    private String message;
    private T responseBody;

    public ApiResponse(
            int status,
            String message,
            T responseBody) {

        this.statusCode = status;
        this.message = message;
        this.responseBody = responseBody;
    }

    public <T> ApiResponse(HttpStatus httpStatus, String success, T body) {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<>(
                HttpStatus.OK,
                "Success",
                body
        );
    }

    public static <T> ApiResponse<T> error(
            HttpStatus status,
            String message) {

        return new ApiResponse<>(
                status,
                message,
                null
        );
    }
}