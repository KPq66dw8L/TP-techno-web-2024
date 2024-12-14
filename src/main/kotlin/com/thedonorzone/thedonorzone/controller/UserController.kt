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
        session.invalidate()
        return "redirect:/login"
    }

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        return "Register"
    }

    @PostMapping("/register")
    fun registerUser(
        @RequestParam("email") email: String?,
        @RequestParam("username") username: String?,
        @RequestParam("password") password: String?,
        session: HttpSession
    ): String {
        // Validate inputs
        if (email.isNullOrBlank() || username.isNullOrBlank() || password.isNullOrBlank()) {
            return "redirect:/register?error=All fields are required"
        }

        return try {
            val user = userService.registerUser(username, email, password)

            val token = userService.authenticateUser(
                username = username,
                email = email,
                password = password
            )

            // Store the JWT in the session
            session.setAttribute("jwtToken", token)

            "redirect:/"
        } catch (e: RuntimeException) {
            "redirect:/register?error=${e.message}"
        }
    }

    @GetMapping("/login")
    fun showLoginForm(model: Model): String {
        return "Login"
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
