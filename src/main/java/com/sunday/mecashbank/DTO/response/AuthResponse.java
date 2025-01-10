package com.sunday.mecashbank.DTO.response;

import com.sunday.mecashbank.enums.ROLE;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String Title;
    private String message;
    private ROLE role;
}