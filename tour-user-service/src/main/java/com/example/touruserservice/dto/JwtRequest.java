package com.example.touruserservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    @NotEmpty(message = "Username should not be empty!")
    private String username;

    @NotEmpty(message = "Password should not be empty!")
    private String password;
}
