package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
@AllArgsConstructor
@Log4j2
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 5, sort = "userId", direction = Sort.Direction.ASC)
                    Pageable pageable
    ) {
        log.info("Get All Users");

        Page<UserModel> users = userService.findAll(pageable, spec);

        if(users.isEmpty()) {
            return ResponseEntity.ok(users);
        }

        users.toList().forEach(user -> {
            user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
        });

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable("userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findOne(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(userModelOptional.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findOne(userId);

        if (userModelOptional.isEmpty()) {
            log.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userService.delete(userId);

        log.info("User {} deleted", userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") UUID userId,
            @RequestBody
            @Validated(UserDto.UserView.UserPut.class)
            @JsonView(UserDto.UserView.UserPut.class) UserDto userDto
    ) {
        Optional<UserModel> userModelOptional = userService.findOne(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        System.out.println(userDto);
        var userModel = userModelOptional.get();
        userModel.setFullName(userDto.getFullName());
        userModel.setPhoneNumber(userDto.getPhoneNumber());
        userModel.setCpf(userDto.getCpf());

        userService.save(userModel);

        log.debug("User updated");
        log.info("User {} updated", userId);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(
            @PathVariable("userId") UUID userId,
            @RequestBody
            @Validated(UserDto.UserView.PasswordPut.class)
            @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto
    ) {
        var userModelOptional = userService.findOne(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userModelOptional.get();

        if (!userModel.getPassword().equals(userDto.getOldPassword())) {
            log.warn("Password is not the same");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The passwords are not equal");
        }

        userModel.setPassword(userDto.getPassword());
        userService.save(userModel);

        log.info("User {} has updated its password", userId);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(
            @PathVariable("userId") UUID userID,
            @RequestBody
            @Validated(UserDto.UserView.ImagePut.class)
            @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto
    ) {
        var userModelOptional = userService.findOne(userID);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userModelOptional.get();
        userModel.setImageUrl(userDto.getImageUrl());

        userService.save(userModel);


        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }


}
