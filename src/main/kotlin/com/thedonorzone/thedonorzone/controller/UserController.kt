package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/users")
class UserController @Autowired constructor(
    private val userService: UserService
) {

    // Endpoint to register a new user
//    @PostMapping("/register")
//    fun registerUser(@RequestBody userRequest: User): ResponseEntity<User> {
//        val newUser = userRequest.username?.let { userRequest.email?.let { it1 ->
//            userService.registerUser(it,
//                it1, userRequest.password)
//        } }
//        return ResponseEntity.ok(newUser)  // Return the newly created user (without token)
//    }

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        return "HTML/Register" // This corresponds to a Thymeleaf template named "Register.html"
    }

    // Controller method adapted to work with the provided Thymeleaf HTML page
    @PostMapping("/register")
    fun registerUser(
        @RequestParam("email") email: String?,
        @RequestParam("username") username: String?,
        @RequestParam("password") password: String?
    ): String {
        // Validate inputs
        if (email.isNullOrBlank() || username.isNullOrBlank() || password.isNullOrBlank()) {
            return "redirect:/register?error=All fields are required" // Redirect back with error
        }

        // Try to register the user
        return try {
            userService.registerUser(username, email, password)
            "redirect:/" // Redirect to login page on success
        } catch (e: RuntimeException) {
            "redirect:/register?error=${e.message}" // Redirect back with error message
        }
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
