package com.ead.notificationhex.adapters.configs

import com.ead.notificationhex.NotificationHexApplication
import com.ead.notificationhex.core.ports.NotificationPersistencePort
import com.ead.notificationhex.core.services.NotificationServicePortImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [NotificationHexApplication::class])
class BeanConfiguration {

    @Bean
    fun notificationServicePortImpl(persistence: NotificationPersistencePort) : NotificationServicePortImpl {
        return NotificationServicePortImpl(persistence)
    }


}