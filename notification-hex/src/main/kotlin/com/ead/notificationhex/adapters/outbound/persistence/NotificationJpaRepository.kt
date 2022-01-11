package com.ead.notificationhex.adapters.outbound.persistence

import com.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity
import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NotificationJpaRepository : JpaRepository<NotificationEntity, UUID> {

    fun findAllByUserIdAndNotificationStatus(
        userId: UUID,
        notificationStatusDomain: NotificationStatusDomain,
        pageable: Pageable
    ): Page<NotificationEntity>

    fun findByNotificationIdAndUserId(
        notificationId: UUID,
        userId: UUID
    ): NotificationEntity?


}