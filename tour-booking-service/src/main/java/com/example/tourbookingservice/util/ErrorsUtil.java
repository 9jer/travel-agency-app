package com.example.tourbookingservice.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnAllErrors(BindingResult bindingResult) {
        StringBuilder errorsMsg = new StringBuilder();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            errorsMsg.append(fieldError.getField() + ": " + fieldError.getDefaultMessage()).append(";");
        }

        throw new BookingException(errorsMsg.toString());
    }
}
