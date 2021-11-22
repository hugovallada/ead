package com.ead.authuser.dtos;

import com.ead.authuser.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface UserView {
       interface RegistrationPost {}
       interface UserPut{}
       interface PasswordPut {}
       interface ImagePut {}
    }

    UUID userId;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    String username;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email(groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    String password;

    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.PasswordPut.class})
    String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    String phoneNumber;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView({UserView.ImagePut.class})
    String imageUrl;
}
