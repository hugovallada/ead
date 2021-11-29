package com.ead.course.dtos;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class SubscriptionDto {

    @NotNull
    UUID userId;

}
