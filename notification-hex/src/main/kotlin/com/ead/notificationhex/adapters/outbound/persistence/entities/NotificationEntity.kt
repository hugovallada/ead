package com.ead.notificationhex.adapters.outbound.persistence.entities

import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_NOTIFICATION")
class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var notificationId: UUID? = null,
    var userId: UUID? = null,
    var title: String? = null,
    var message: String? = null,
    var creationDate: LocalDateTime? = null,
    @Enumerated(value = EnumType.STRING)
    var notificationStatus: NotificationStatusDomain? = null
) {
}