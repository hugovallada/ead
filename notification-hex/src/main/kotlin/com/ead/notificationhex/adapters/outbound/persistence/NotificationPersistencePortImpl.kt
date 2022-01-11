package com.ead.notificationhex.adapters.outbound.persistence

import com.ead.notificationhex.adapters.converter.NotificationConverter
import com.ead.notificationhex.core.domain.NotificationDomain
import com.ead.notificationhex.core.domain.PageInfo
import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import com.ead.notificationhex.core.ports.NotificationPersistencePort
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotificationPersistencePortImpl(private val notificationJpaRepository: NotificationJpaRepository) :
    NotificationPersistencePort {

    val converter: NotificationConverter = Mappers.getMapper(NotificationConverter::class.java)

    override fun save(notificationDomain: NotificationDomain): NotificationDomain {
        converter.convertToEntity(notificationDomain).run {
            notificationJpaRepository.save(this).run {
                return converter.convertToDomain(this)
            }
        }
    }

    override fun findAllByUserIdAndNotificationStatus(
        userId: UUID,
        status: NotificationStatusDomain,
        pageInfo: PageInfo
    ): List<NotificationDomain> {
        val page = PageRequest.of(pageInfo.pageNumber, pageInfo.pageSize)

        return notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, status, page).run {
           map { converter.convertToDomain(it) }
        }.toList()
    }

    override fun findByNotificationIdAndUserId(notificationId: UUID, userId: UUID): NotificationDomain? {
        return notificationJpaRepository.findByNotificationIdAndUserId(notificationId, userId)?.let {
            converter.convertToDomain(it)
        }
    }
}