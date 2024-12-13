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
        val newUser = userService.registerUser(userRequest.username,userRequest.email, userRequest.password)
        return ResponseEntity.ok(newUser)  // Return the newly created user (without token)
    }

    // Endpoint to authenticate a user and return a JWT token
    @PostMapping("/login")
    fun authenticateUser(@RequestBody userRequest: User): ResponseEntity<String> {
        val token = userService.authenticateUser(userRequest.username, userRequest.password)
        return ResponseEntity.ok(token)  // Return the JWT token on successful login
    }
}
