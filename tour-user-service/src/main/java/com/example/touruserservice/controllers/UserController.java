package com.example.touruserservice.controllers;

import com.example.touruserservice.dto.GetUserDTO;
import com.example.touruserservice.dto.SaveUserDTO;
import com.example.touruserservice.dto.UserDTO;
import com.example.touruserservice.dto.UsersResponse;
import com.example.touruserservice.models.User;
import com.example.touruserservice.services.UserService;
import com.example.touruserservice.util.ErrorResponse;
import com.example.touruserservice.util.ErrorsUtil;
import com.example.touruserservice.util.UserException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponse getAllUsers() {
        return new UsersResponse(userService.findAll().stream().map(this::convertUserToGetUserDTO).toList());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") Long id, @RequestBody @Valid SaveUserDTO saveUserDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            ErrorsUtil.returnAllErrors(bindingResult);
        }

        userService.updateUserById(id, saveUserDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public GetUserDTO getUserById(@PathVariable("id") Long id) {
        return convertUserToGetUserDTO(userService.getUserById(id));
    }

    @PostMapping("/{id}/assign-admin")
    public ResponseEntity<UserDTO> assignAdminRole(@PathVariable("id") Long id) {
        User updatedUser = userService.assignAdminRole(id);
        return ResponseEntity.ok(convertUserToUserDTO(updatedUser));
    }

    @GetMapping("/{id}/exists")
    public Boolean userExists(@PathVariable Long id) {
        return userService.existsById(id);
    }

    @GetMapping("/info")
    public String userData(Principal principal){
        return principal.getName();
    }

    private UserDTO convertUserToUserDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertUserDTOToUser(SaveUserDTO saveUserDTO){
        return modelMapper.map(saveUserDTO, User.class);
    }

    private GetUserDTO convertUserToGetUserDTO(User user){
        return modelMapper.map(user, GetUserDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
