package com.thedonorzone.thedonorzone.service

import UserDto
import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(userDto: UserDto): User {
        // Check if user already exists
        userRepository.findByEmail(userDto.email)?.let {
            throw RuntimeException("User already exists")
        }

        val user = User(email = userDto.email, password = userDto.password) // Hash password in a real app
        return userRepository.save(user)
    }
}