package com.sunday.mecashbank.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
public enum INTERNAL_BANK_CODE {
    ME_CASH_BANK_000(200, "Request performed successfully", OK),
    ME_CASH_BANK_001(201, "Request created successfully", OK),
    ME_CASH_BANK_003(403, "Access denied", HttpStatus.FORBIDDEN),
    ME_CASH_BANK_004(400, "User already exists", HttpStatus.BAD_REQUEST),
    ME_CASH_BANK_404(404, "User not found", HttpStatus.NOT_FOUND),
    CARE_PULSE_005(500, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String codeDescription;
    private final String codeNumber;
    private final HttpStatus httpStatus;

    INTERNAL_BANK_CODE(int codeNumber, String codeDescription, HttpStatus status) {
        this.codeNumber = String.format("%03d", codeNumber);
        this.codeDescription = codeDescription;
        this.httpStatus = status;
    }
}
