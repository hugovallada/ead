package com.ead.notificationhex.adapters.converter

import com.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity
import com.ead.notificationhex.core.domain.NotificationDomain
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface NotificationConverter {

    @Mapping(source = "title", target = "title")
    fun convertToDomain(notification: NotificationEntity) : NotificationDomain

    @InheritInverseConfiguration
    fun convertToEntity(notificationDomain: NotificationDomain): NotificationEntity
}