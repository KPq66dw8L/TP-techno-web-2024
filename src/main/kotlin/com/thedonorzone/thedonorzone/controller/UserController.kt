package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(
    private val userService: UserService
) {

    // Endpoint to register a new user
    @PostMapping("/register")
    fun registerUser(@RequestBody userRequest: User): ResponseEntity<User> {
        val newUser = userRequest.username?.let { userRequest.email?.let { it1 ->
            userService.registerUser(it,
                it1, userRequest.password)
        } }
        return ResponseEntity.ok(newUser)  // Return the newly created user (without token)
    }

    // Endpoint to authenticate a user and return a JWT token
    @PostMapping("/login")
    fun authenticateUser(@RequestBody userRequest: User): ResponseEntity<String> {
        // Ensure that either username or email is provided
        if (userRequest.username.isNullOrBlank() && userRequest.email.isNullOrBlank()) {
            throw RuntimeException("Username or email must be provided!")
        }

        // Authenticate using username or email and password
        val token = userService.authenticateUser(
            username = userRequest.username,
            email = userRequest.email,
            password = userRequest.password
        )

        // Check if the authentication returned a token
        return if (token != null) {
            ResponseEntity.ok(token) // Return the JWT token on successful login
        } else {
            ResponseEntity.status(401).body("Invalid credentials!") // Handle failed authentication
        }
    }

}
