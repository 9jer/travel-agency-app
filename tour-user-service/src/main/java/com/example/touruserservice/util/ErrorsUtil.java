package com.example.touruserservice.util;

import com.example.touruserservice.dto.SaveUserDTO;
import com.example.touruserservice.models.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ErrorsUtil {

    public static void returnAllErrors(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for(FieldError fieldError : fieldErrors) {
            errorMsg.append(fieldError.getField() + ": " + fieldError.getDefaultMessage()).append(";");
        }

        throw new UserException(errorMsg.toString());
    }

    public static void validateInputUserData(SaveUserDTO saveUserDTO, Optional<User> userByUsername, Optional<User> userByEmail) {
        if(!saveUserDTO.getPassword().equals(saveUserDTO.getConfirmPassword())){
            throw new UserException("Incorrect password!");
        }

        if(userByUsername.isPresent() && !Objects.equals(saveUserDTO.getUsername(), userByUsername.get().getUsername())){
            throw new UserException("User with username " + saveUserDTO.getUsername()
                    + " already exist");
        }

        if(userByEmail.isPresent() && !Objects.equals(saveUserDTO.getEmail(), userByEmail.get().getEmail())){
            throw new UserException("Email " + saveUserDTO.getEmail()
                    + " address already in use!");
        }
    }
}
