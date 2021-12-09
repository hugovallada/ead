package com.ead.authuser.service.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserEventPublisher userEventPublisher;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable, SpecificationTemplate.UserSpec spec) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<UserModel> findOne(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserModel save(UserModel userModel) {

        userRepository.save(userModel);
        return userModel;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel) {
        userModel = save(userModel);

        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
        return userModel;
    }
}
