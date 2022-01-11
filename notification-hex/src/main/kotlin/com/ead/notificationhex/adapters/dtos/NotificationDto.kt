package com.ead.notificationhex.adapters.dtos

import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain

data class NotificationDto(
    val notificationStatus: NotificationStatusDomain
)
