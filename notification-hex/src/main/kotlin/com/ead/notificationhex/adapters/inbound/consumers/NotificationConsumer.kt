package com.ead.notificationhex.adapters.inbound.consumers

import com.ead.notificationhex.adapters.dtos.NotificationCommandDto
import com.ead.notificationhex.core.domain.NotificationDomain
import com.ead.notificationhex.core.domain.enums.NotificationStatusDomain
import com.ead.notificationhex.core.ports.NotificationServicePort
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class NotificationConsumer(private val notificationServicePort: NotificationServicePort) {

    @RabbitListener(
        bindings = [
            QueueBinding(
                value = Queue(value = "\${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
                exchange = Exchange(value = "\${ead.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
                key = ["\${ead.broker.key.notificationCommandKey}"]
            )
        ]
    )
    fun listen(@Payload notificationCommandDto: NotificationCommandDto) {
        val notification = NotificationDomain(
            userId = notificationCommandDto.userId,
            title = notificationCommandDto.title,
            message = notificationCommandDto.message
        )

        notification.creationDate = LocalDateTime.now()
        notification.notificationStatus = NotificationStatusDomain.CREATED
        notificationServicePort.saveNotification(notification)

    }

}