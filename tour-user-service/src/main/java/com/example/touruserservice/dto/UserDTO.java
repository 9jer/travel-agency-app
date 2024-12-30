package com.example.touruserservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Username should not be empty!")
    private String username;

    @NotEmpty(message = "Email should not be empty!")
    private String email;

    @NotEmpty(message = "Name should not be empty!")
    private String name;

    @NotEmpty(message = "Phone should not be empty!")
    private String phone;
}
