package com.ead.authuser.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class JwtDto {

    private final String token;

    private final String type = "Bearer ";
}
