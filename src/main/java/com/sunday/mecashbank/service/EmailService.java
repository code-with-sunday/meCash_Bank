package com.sunday.mecashbank.service;

import com.sunday.mecashbank.DTO.request.MailRequest;

public interface EmailService {
    void sendEmailAlert(MailRequest mailDTO);
}
