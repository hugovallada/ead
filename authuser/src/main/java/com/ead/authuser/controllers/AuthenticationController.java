package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.RoleService;
import com.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/auth")
@AllArgsConstructor
@Log4j2
public class AuthenticationController {

    private UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto
    ) {
        log.debug("POST: registerUser userDto received {}", userDto.toString());
        if (userService.existsByUsername(userDto.getUsername())) {
            log.warn("Username {} is already taken.", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already taken.");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            log.warn("Email {} is already taken.", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email already in use.");
        }

        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.getRoles().add(roleModel);
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userModel = userService.saveUser(userModel);

        userModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getOneUser(userModel.getUserId())).withSelfRel());

        log.debug("POST registerUser userModel saved {}", userModel.toString());
        log.info("User {} saved successfully", userDto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

}
