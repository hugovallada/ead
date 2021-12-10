package com.ead.course.dtos;

import com.ead.course.models.UserModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEventDto {

    UUID userId;

    String username;

    String email;

    String fullName;

    String userStatus;

    String userType;

    String phoneNumber;

    String cpf;

    String imageUrl;

    String actionType;

    public UserModel convertToUserModel() {
        var userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        userModel.setId(this.userId);
        return userModel;
    }
}
