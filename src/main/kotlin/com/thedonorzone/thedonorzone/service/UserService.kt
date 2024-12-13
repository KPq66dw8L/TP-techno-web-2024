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
    fun registerUser(username: String,email: String, password: String): User {
        // Check if username already exists
        if (userRepository.findByUsername(username) != null) {
            throw RuntimeException("Username already exists!")
        }

        // Encode password
        val encodedPassword = passwordEncoder.encode(password)

        // Create and save the new user
        val newUser = User(username=username, email=email, password=encodedPassword)
        return userRepository.save(newUser)
    }

    // Authenticate a user and generate JWT token
    fun authenticateUser(username: String, password: String): String {
        // Find the user by username
        val optionalUser = userRepository.findByUsername(username)
        if (optionalUser == null) {
            throw RuntimeException("Invalid username or password!")
        }

        // Check if the password matches
        if (!passwordEncoder.matches(password, optionalUser.password)) {
            throw RuntimeException("Invalid username or password!")
        }

        // Generate JWT token
        return jwtTokenProvider.generateToken(username)
    }
}
