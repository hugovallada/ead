package com.ead.notificationhex.core.ports

import com.ead.notificationhex.core.domain.NotificationDomain
import com.ead.notificationhex.core.domain.PageInfo
import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import java.util.*

interface NotificationPersistencePort {

    fun save(notificationDomain: NotificationDomain): NotificationDomain

    fun findAllByUserIdAndNotificationStatus(userId: UUID, status: NotificationStatusDomain, pageInfo: PageInfo) : List<NotificationDomain>

    fun findByNotificationIdAndUserId(notificationId: UUID, userId: UUID) : NotificationDomain?

}