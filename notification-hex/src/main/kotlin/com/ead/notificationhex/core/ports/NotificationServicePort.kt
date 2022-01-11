package com.ead.notificationhex.core.ports

import com.ead.notificationhex.core.domain.NotificationDomain
import com.ead.notificationhex.core.domain.PageInfo
import java.util.*

interface NotificationServicePort {

    fun saveNotification(notificationDomain: NotificationDomain): NotificationDomain

    fun findAllNotificationsByUser(userId: UUID, pageable: PageInfo): List<NotificationDomain>

    fun findByNotificationIdAndUserId(notificationId: UUID, userId: UUID): NotificationDomain?


}