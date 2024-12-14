package com.thedonorzone.thedonorzone.controller

import com.thedonorzone.thedonorzone.model.User
import com.thedonorzone.thedonorzone.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class UserController @Autowired constructor(
    private val userService: UserService
) {

    @PostMapping("/logout")
    fun logout(session: HttpSession): String {
        // Invalidate the session and remove the JWT
        session.invalidate()
        return "redirect:/login" // Redirect to login page after logout
    }

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        return "HTML/Register" // This corresponds to a Thymeleaf template named "Register.html"
    }

    // Controller method adapted to work with the provided Thymeleaf HTML page
    @PostMapping("/register")
    fun registerUser(
        @RequestParam("email") email: String?,
        @RequestParam("username") username: String?,
        @RequestParam("password") password: String?,
        session: HttpSession
    ): String {
        // Validate inputs
        if (email.isNullOrBlank() || username.isNullOrBlank() || password.isNullOrBlank()) {
            return "redirect:/register?error=All fields are required" // Redirect back with error
        }

        // Try to register the user
        return try {
            val user = userService.registerUser(username, email, password)

            // Generate token after successful registration
            val token = userService.authenticateUser(
                username = username,
                email = email,
                password = password
            )

            // Store the JWT in the session
            session.setAttribute("jwtToken", token)

            // Redirect to home page after successful registration
            "redirect:/"
        } catch (e: RuntimeException) {
            "redirect:/register?error=${e.message}" // Redirect back with error message
        }
    }

    @GetMapping("/login")
    fun showLoginForm(model: Model): String {
        return "HTML/Login" // Returns the Thymeleaf template named "Login.html"
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam("email") email: String?,
        @RequestParam("username") username: String?,
        @RequestParam("password") password: String?,
        session: HttpSession
    ): String {
        // Validate inputs
        if ((email.isNullOrBlank() && username.isNullOrBlank()) || password.isNullOrBlank()) {
            return "redirect:/login?error=Username/Email and password are required" // Redirect back with error
        }

        // Try to authenticate the user
        return try {
            val token = userService.authenticateUser(username, email, password)
            if (token != null) {
                session.setAttribute("jwtToken", token)

                "redirect:/" // Redirect to the home page on success
            } else {
                "redirect:/login?error=Invalid credentials" // Redirect back with error
            }
        } catch (e: RuntimeException) {
            "redirect:/login?error=${e.message}" // Redirect back with error message
        }
    }


}
