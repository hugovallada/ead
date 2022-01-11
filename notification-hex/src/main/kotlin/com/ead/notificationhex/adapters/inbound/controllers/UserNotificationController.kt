package com.ead.notificationhex.adapters.inbound.controllers

import com.ead.notificationhex.adapters.dtos.NotificationDto
import com.ead.notificationhex.core.domain.NotificationDomain
import com.ead.notificationhex.core.domain.PageInfo
import com.ead.notificationhex.core.ports.NotificationServicePort
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController("/users/{userId}/notifications")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserNotificationController(private val notificationServicePort: NotificationServicePort) {

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    fun getAllNotificationByUser(
        @PathVariable userId: UUID,
        @PageableDefault pageable: Pageable,
        authentication: Authentication
    ): ResponseEntity<Page<NotificationDomain>> {
        notificationServicePort.findAllNotificationsByUser(userId, PageInfo(pageable.pageNumber, pageable.pageSize))
            .run {
                return ResponseEntity.ok(PageImpl(this, pageable, size.toLong()))
            }
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/{notificationId}")
    fun updateNotification(
        @PathVariable userId: UUID,
        @PathVariable notificationId: UUID,
        @RequestBody @Valid notificationDto: NotificationDto
    ): ResponseEntity<Any> {
        val notification = notificationServicePort.findByNotificationIdAndUserId(notificationId, userId)
            ?: return ResponseEntity.notFound().build()

        notification.notificationStatus = notificationDto.notificationStatus
        notificationServicePort.saveNotification(notification)

        return ResponseEntity.ok(notification)
    }
}