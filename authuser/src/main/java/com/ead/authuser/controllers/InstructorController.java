package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.service.RoleService;
import com.ead.authuser.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
@Log4j2
@AllArgsConstructor
public class InstructorController {

    private final UserService userService;

    private final RoleService roleService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
        var instructorOptional = userService.findOne(instructorDto.getUserId());

        if(instructorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_INSTRUCTOR)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));

        var user = instructorOptional.get();
        user.setUserType(UserType.INSTRUCTOR);
        user.getRoles().add(roleModel);
        userService.updateUser(user);
        return ResponseEntity.status(202).body(user);
    }


}
