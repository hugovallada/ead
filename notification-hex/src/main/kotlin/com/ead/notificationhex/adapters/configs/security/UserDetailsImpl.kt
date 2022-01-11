package com.ead.notificationhex.adapters.configs.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserDetailsImpl(
    val userId: UUID,
) : UserDetails {

    private lateinit var authorities: MutableCollection<out GrantedAuthority>

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    constructor(userId: UUID, authorities: MutableCollection<out GrantedAuthority>) : this(userId) {
        this.authorities = authorities
    }

    companion object {
        fun build(userId: UUID, rolesStr: String)  : UserDetailsImpl{
           val auth = rolesStr.split(",").map {
               SimpleGrantedAuthority(it)
           } as MutableCollection<out GrantedAuthority>

            return UserDetailsImpl(userId, auth)
        }
    }
}