package com.ead.course.dtos;

import lombok.Value;

import java.util.UUID;

@Value
public class UserDto {
    UUID userId;
    String username;
    String email;
    String fullName;
    String phoneNumber;
    String cpf;
    String imageUrl;
    UserStatus userStatus;
    UserType userType;
}
