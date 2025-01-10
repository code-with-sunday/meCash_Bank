package com.sunday.mecashbank.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunday.mecashbank.enums.INTERNAL_BANK_CODE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseBody<T> {
    private final boolean success;
    private final String message;
    private final INTERNAL_BANK_CODE internalCode;
    private final String timestamp = new Timestamp(System.currentTimeMillis())
            .toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+0000"));
    private final T data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wrapper<T> {
        private T content;
        private int totalPages;
        private long totalItems;
        private int totalItemsCurrentPage;
        private int currentPage;
        private boolean isLastPage;
        private boolean isFirstPage;
        private boolean isEmpty;
    }
}
