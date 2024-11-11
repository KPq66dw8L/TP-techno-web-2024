package com.thedonorzone.thedonorzone.controller

import UserDto
import com.thedonorzone.thedonorzone.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    /**
     * Endpoint for creating a new user.
     */
    @PostMapping("/register")
    fun registerUser(@RequestBody userDto: UserDto): ResponseEntity<String> {
        userService.createUser(userDto)
        return ResponseEntity.ok("User registered successfully")
    }

    /**
     * Endpoint for user login. Returns JWT token if successful.
     */
    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: Map<String, String>): ResponseEntity<Map<String, String>> {
        val email = loginRequest["email"] ?: return ResponseEntity.badRequest().body(mapOf("error" to "Email is required"))
        val password = loginRequest["password"] ?: return ResponseEntity.badRequest().body(mapOf("error" to "Password is required"))

        // Generate and return JWT token
        val token = userService.loginUser(email, password)
        return ResponseEntity.ok(mapOf("token" to token))
    }
}
