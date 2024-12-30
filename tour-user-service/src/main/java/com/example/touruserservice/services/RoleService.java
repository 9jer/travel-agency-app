package com.example.touruserservice.services;

import com.example.touruserservice.models.Role;
import com.example.touruserservice.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN").get();
    }

    public Role getGuestRole() {
        return roleRepository.findByName("ROLE_GUEST")
                .orElseThrow(() -> new RuntimeException("Role GUEST not found"));
    }

}
