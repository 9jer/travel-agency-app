package com.example.touruserservice.dto;

import com.example.touruserservice.models.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {

    private Long id;

    @NotEmpty(message = "Username should not be empty!")
    private String username;

    @NotEmpty(message = "Email should not be empty!")
    private String email;

    @NotEmpty(message = "Name should not be empty!")
    private String name;

    @NotEmpty(message = "Phone should not be empty!")
    private String phone;

    private Collection<Role> roles;
}
