package com.sunday.mecashbank.DTO.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String responseCode;
    private String message;
    private T data;

    public ApiResponse(boolean success, String responseCode, String message, T data) {
        this.success = success;
        this.responseCode = responseCode;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(boolean success, String responseCode, String message) {
        this.success = success;
        this.responseCode = responseCode;
        this.message= message;
    }
}