package com.sunday.mecashbank.DTO.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunday.mecashbank.enums.ROLE;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String Title;
    private String message;
    private ROLE role;
}