package com.ead.notificationhex.core.domain

import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import java.time.LocalDateTime
import java.util.*

data class NotificationDomain(

    var notificationId: UUID? = null,
    var userId: UUID? = null,
    var title: String? = null,
    var message: String? = null,
    var creationDate: LocalDateTime? = null,
    var notificationStatus: NotificationStatusDomain? = null

) {
}