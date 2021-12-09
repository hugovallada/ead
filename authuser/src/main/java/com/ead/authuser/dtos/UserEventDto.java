package com.ead.authuser.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldDefaults;

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
}
