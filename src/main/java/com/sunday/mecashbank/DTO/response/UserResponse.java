package com.sunday.mecashbank.DTO.response;

import com.sunday.mecashbank.enums.ROLE;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private ROLE role;
}
