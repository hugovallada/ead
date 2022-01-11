package com.ead.notificationhex.adapters.configs.security

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProvider {

    @Value(value = "\${ead.auth.jwtSecret}")
    var jwtSecret: String? = null

    fun getSubjectJwt(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun getClaimNameJwt(token: String, claimName: String): String {
        return Jwts
            .parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body[claimName]
            .toString()
    }

    fun validateJwt(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

}