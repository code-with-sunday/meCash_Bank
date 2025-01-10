package com.sunday.mecashbank.DTO.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailRequest {
    private String to;
    private String subject;
    private String message;
}

