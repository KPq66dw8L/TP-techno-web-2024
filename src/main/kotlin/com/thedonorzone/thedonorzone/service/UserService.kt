package com.thedonorzone.thedonorzone.service

import UserDto
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.UserRepository
import com.thedonorzone.thedonorzone.security.JwtTokenUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    /**
     * Create a new user with hashed password.
     */
    @Transactional
    fun createUser(userDto: UserDto): User {
        // Check if user already exists by email
        userRepository.findByEmail(userDto.email)?.let {
            throw RuntimeException("User with email ${userDto.email} already exists")
        }

        // Create user object with hashed password
        val user = User(
            email = userDto.email,
            username = userDto.username,
            password = passwordEncoder.encode(userDto.password) // Hash the password
        )

        // Save and return the new user
        return userRepository.save(user)
    }

    /**
     * Validate user credentials and return a JWT token.
     */
    fun loginUser(email: String, password: String): String {
        // Find the user by email
        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("Invalid email")

        // Check the password
        if (!passwordEncoder.matches(password, user.password)) {
            throw RuntimeException("Invalid password")
        }

        // Generate JWT token
        return JwtTokenUtil.generateToken(user.username)
    }

    /**
     * Find a user by their ID.
     */
    fun findById(userId: UUID): User? {
        return userRepository.findById(userId)
    }
}
