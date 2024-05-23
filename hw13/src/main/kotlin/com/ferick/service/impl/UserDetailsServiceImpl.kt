package com.ferick.service.impl

import com.ferick.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found")
        val roles = user.roles.map { it.role }.toTypedArray().ifEmpty {
            throw UsernameNotFoundException("User $username has no authority")
        }
        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles(*roles)
            .build()
    }
}
