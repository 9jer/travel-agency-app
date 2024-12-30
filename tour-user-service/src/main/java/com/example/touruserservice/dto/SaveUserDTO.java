package com.example.touruserservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserDTO {
    @NotEmpty(message = "Username should not be empty!")
    private String username;

    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Email is incorrect!")
    private String email;

    @NotEmpty(message = "Password should not be empty!")
    private String password;

    @NotEmpty(message = "Please confirm the password!")
    private String confirmPassword;

    @NotEmpty(message = "Name should not be empty!")
    private String name;

    @NotEmpty(message = "Phone should not be empty!")
    private String phone;

}
