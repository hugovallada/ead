package com.ead.notificationhex.adapters.configs.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationCurrentUserService {

    fun getCurrentUser() : UserDetailsImpl {
        return SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
    }

}