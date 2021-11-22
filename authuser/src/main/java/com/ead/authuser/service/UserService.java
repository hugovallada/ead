package com.ead.authuser.service;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Page<UserModel> findAll(Pageable pageable, SpecificationTemplate.UserSpec spec);

    Optional<UserModel> findOne(UUID id);

    void delete(UUID id);

    void save(UserModel userModel);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
