package com.thedonorzone.thedonorzone.service

import UserDto
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.UserRepository
import com.thedonorzone.thedonorzone.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    // Register a user
    fun registerUser(username: String?, email: String?, password: String?): User {
        // Validate inputs
        if (username.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank()) {
            throw IllegalArgumentException("Username, email, and password must not be null or blank")
        }

        // Check if username already exists
        if (userRepository.findByUsername(username) != null) {
            throw RuntimeException("Username already exists!")
        }

        // Check if email already exists
        if (userRepository.findByEmail(email) != null) {
            throw RuntimeException("Email already exists!")
        }

        // Encode password
        val encodedPassword = passwordEncoder.encode(password)

        // Create and save the new user
        val newUser = User(username = username, email = email, password = encodedPassword)
        return userRepository.save(newUser)
    }

    // Authenticate a user and generate JWT token
    fun authenticateUser(username: String?, email: String?, password: String?): String? {
        // Validate inputs
        if (password.isNullOrBlank()) {
            throw IllegalArgumentException("Password must not be null or blank")
        }
        if (username.isNullOrBlank() && email.isNullOrBlank()) {
            throw IllegalArgumentException("Either username or email must be provided")
        }

        val user = when {
            !username.isNullOrBlank() -> userRepository.findByUsername(username)
            !email.isNullOrBlank() -> userRepository.findByEmail(email)
            else -> null
        }

        // Check if user exists
        if (user == null || !passwordEncoder.matches(password, user.password)) {
            throw RuntimeException("Invalid username, email, or password!")
        }

        // Generate JWT token
        return user.username?.let { jwtTokenProvider.generateToken(it) }
    }
}
