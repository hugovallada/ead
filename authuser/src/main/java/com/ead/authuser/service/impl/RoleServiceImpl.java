package com.ead.authuser.service.impl;

import com.ead.authuser.enums.RoleType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.repository.RoleRepository;
import com.ead.authuser.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
