package com.ead.notificationhex.adapters.configs.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationJwtFilter() : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtProvider: JwtProvider

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwtStr = getToken(request)

            if (jwtStr != null && jwtProvider.validateJwt(jwtStr)) {
                val userId = jwtProvider.getSubjectJwt(jwtStr)
                val rolesStr = jwtProvider.getClaimNameJwt(jwtStr, "roles")
                val userDetails = UserDetailsImpl.build(UUID.fromString(userId),rolesStr)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            println("Cannot set user Authentication: $e")
        }

        filterChain.doFilter(request, response)
    }

    fun getToken(request: HttpServletRequest) : String? {
        val headerAuth = request.getHeader("Authorization")

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length)
        }
        return null
    }
}