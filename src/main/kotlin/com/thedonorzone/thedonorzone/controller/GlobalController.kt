package com.thedonorzone.thedonorzone.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@RestController
//@RequestMapping("/api/auth")
//class AuthController {
//    @PostMapping("/login")
//    fun login(@RequestBody request: Map<String, String>): ResponseEntity<Map<String, String>> {
//        val username = request["username"] ?: return ResponseEntity.badRequest().build()
//
//        // Optionally, validate the username and password here
//        val token = JwtTokenUtil.generateToken(username)
//        return ResponseEntity.ok(mapOf("token" to token))
//    }
//}

@Controller
class GlobalController {
    @RequestMapping("/")
    fun home(model: Model): String {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        // If the user is not authenticated, redirect to the login page
        if (authentication == null || !authentication.isAuthenticated || authentication.name == "anonymousUser") {
            return "redirect:/login"  // Redirect to login page
        }
        return "index"
    }
}