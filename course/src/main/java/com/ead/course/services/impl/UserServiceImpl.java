package com.ead.course.services.impl;

import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

}
