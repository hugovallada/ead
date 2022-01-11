package com.ead.notificationhex.adapters.dtos

import java.util.*

data class NotificationCommandDto(
    val title: String,
    val message: String,
    val userId: UUID
)
