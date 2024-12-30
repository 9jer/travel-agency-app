package com.example.touruserservice.services;

import com.example.touruserservice.dto.SaveUserDTO;
import com.example.touruserservice.models.Role;
import com.example.touruserservice.models.User;
import com.example.touruserservice.repositories.UserRepository;
import com.example.touruserservice.util.ErrorsUtil;
import com.example.touruserservice.util.UserException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getAdminRole()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new UserException(String.format("User %s not found", id)));
    }

    @Transactional
    public User updateUserById(Long id, SaveUserDTO updatedUser) {

        ErrorsUtil.validateInputUserData(updatedUser, userRepository.findByUsername(updatedUser.getUsername())
                ,userRepository.findByEmail(updatedUser.getEmail())
        );

        User exsitingUser = getUserById(id);

        User user = convertUserDTOToUser(updatedUser);
        enrichPropertyForUpdate(exsitingUser, user);
        
        return userRepository.save(exsitingUser);
    }


    private User convertUserDTOToUser(SaveUserDTO saveUserDTO){
        return modelMapper.map(saveUserDTO, User.class);
    }

    private void enrichPropertyForUpdate(User exsitingUser, User updatedUser) {
        exsitingUser.setUsername(updatedUser.getUsername());
        exsitingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        exsitingUser.setName(updatedUser.getName());
        exsitingUser.setEmail(updatedUser.getEmail());
        exsitingUser.setPhone(updatedUser.getPhone());
        exsitingUser.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public User assignAdminRole(Long userId) {
        User user = getUserById(userId);
        Role adminRole = roleService.getAdminRole();

        if (user.getRoles().contains(adminRole)) {
            throw new UserException(String.format("User %s already has the Admin role", userId));
        }

        user.getRoles().add(adminRole);
        return userRepository.save(user);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
