package com.example.touruserservice.services;

import com.example.touruserservice.dto.JwtRequest;
import com.example.touruserservice.dto.JwtResponse;
import com.example.touruserservice.dto.SaveUserDTO;
import com.example.touruserservice.models.User;
import com.example.touruserservice.util.AuthException;
import com.example.touruserservice.util.ErrorsUtil;
import com.example.touruserservice.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials: " + authRequest.getUsername());
            throw new AuthException("Incorrect login or password!");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @Transactional
    public User createNewUser(SaveUserDTO saveUserDTO) {

        ErrorsUtil.validateInputUserData(saveUserDTO,
                userService.findByUsername(saveUserDTO.getUsername()),
                userService.findByEmail(saveUserDTO.getEmail()));

        User user = convertRegistrationUserDTOToUser(saveUserDTO);

        return userService.createNewUser(user);
    }

    private User convertRegistrationUserDTOToUser(SaveUserDTO saveUserDTO) {
        return modelMapper.map(saveUserDTO, User.class);
    }
}
