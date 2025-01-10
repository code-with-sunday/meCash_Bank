package com.sunday.mecashbank.DTO.request;

import com.sunday.mecashbank.enums.ROLE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileRequest {
    @NotEmpty(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "firstname is mandatory")
    @NotNull(message = "firstname is mandatory")
    @NotEmpty(message = "firstname is mandatory")
    private String firstName;

    private String middleName;

    @NotBlank(message = "lastname is mandatory")
    @NotNull(message = "lastname is mandatory")
    @NotEmpty(message = "lastname is mandatory")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private ROLE role;
}