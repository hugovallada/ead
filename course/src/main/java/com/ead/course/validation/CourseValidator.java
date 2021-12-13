package com.ead.course.validation;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        var courseDto = (CourseDto) target;
        validator.validate(target, errors);

        if (!errors.hasErrors()) {
            validateUserInstructor(((CourseDto) target).getUserInstructor(), errors);
        }

    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {

        var userModelOptional = userService.findById(userInstructor);

        if (userModelOptional.isEmpty()) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor Not Found");
        }

        var userModel = userModelOptional.get();

        if (userModel.getUserType().equals(UserType.STUDENT.toString())) {
            errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
        }

    }
}
