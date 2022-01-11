package com.ead.notificationhex.core.services

import com.ead.notificationhex.core.domain.NotificationDomain
import com.ead.notificationhex.core.domain.PageInfo
import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import com.ead.notificationhex.core.ports.NotificationPersistencePort
import com.ead.notificationhex.core.ports.NotificationServicePort
import java.util.*

class NotificationServicePortImpl(private val notificationPersistencePort: NotificationPersistencePort) :
    NotificationServicePort {

    override fun saveNotification(notificationDomain: NotificationDomain): NotificationDomain {
        return notificationPersistencePort.save(notificationDomain)
    }

    override fun findAllNotificationsByUser(userId: UUID, pageable: PageInfo): List<NotificationDomain> {
        return notificationPersistencePort.findAllByUserIdAndNotificationStatus(
            userId,
            NotificationStatusDomain.CREATED,
            pageable
        )
    }

    override fun findByNotificationIdAndUserId(notificationId: UUID, userId: UUID): NotificationDomain? {
        return notificationPersistencePort.findByNotificationIdAndUserId(notificationId, userId)
    }
}